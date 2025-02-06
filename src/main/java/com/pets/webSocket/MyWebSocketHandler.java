package com.pets.webSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.util.StringUtil;
import com.pets.service.ConverseService;
import com.pets.service.impl.ConverseServiceImpl;
import com.pets.utils.base.JwtUtils;
import com.pets.webSocket.pojo.AssignCustomerServer;
import com.pets.webSocket.pojo.BroadcastOnline;
import com.pets.webSocket.pojo.EmpWorkLoadVO;
import com.pets.webSocket.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final ConverseServiceImpl converseService;

    public MyWebSocketHandler(ConverseServiceImpl converseService) {
        this.converseService = converseService;
    }

    private static int onlineCount = 0;

    // 存储用户名和会话的映射
    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        // 从 URL 中获取token令牌，在获取用户名
        String token = Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]; // 例如 ws://localhost:8080/ws?token=token
        String username = (String) JwtUtils.parseJWT(token).get("loginUsername");
        String identity = (String) JwtUtils.parseJWT(token).get("loginIdentity");
        session.getAttributes().put("username", username);
        session.getAttributes().put("identity", identity);

        // 将用户名与会话关联
        sessions.put(username, session);
        System.out.println(identity + " : " + username + " 已连接");

        // 广播通知emp顾客在线
        onlineCount++;
        broadcastOnlineCount(username, true);

        // 如果连接的是顾客，分配员工
        if (identity.equals("customer")){
            assignEmp(username);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // 获取发送消息的用户名
        String username = (String) session.getAttributes().get("username");
        String identity = (String) session.getAttributes().get("identity");

        // 解析消息
        String payload = message.getPayload();
        System.out.println("接收到" + identity + " : " + username + "的消息: " + payload);

        // 使用 ObjectMapper 解析 JSON 字符串
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Message msg = objectMapper.readValue(payload, Message.class);

            String receiver = msg.getReceiver();

            // 根据接收者用户名查找对应的 WebSocket 会话
            WebSocketSession receiverSession = sessions.get(receiver);

            if (StringUtil.isEmpty(receiver) && msg.getReceiverType() == 3){
                // 客服向系统发送消息
                systemctlSendMessage();

            } else if (receiverSession != null && receiverSession.isOpen()) {

                // 向接收者发送整个 Message 对象
                receiverSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(msg)));
                System.out.println("消息已发送给 " + receiver);
            } else {
                // 如果接收者不在线，可以给发送者发送提示
                // session.sendMessage(new TextMessage("用户 " + receiver + " 当前不在线"));
                System.out.println("用户 " + receiver + " 当前不在线");
            }


            //将消息录入数据库
            insertMessage(msg);


        } catch (Exception e) {
            e.printStackTrace();
            session.sendMessage(new TextMessage("消息格式错误"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 移除已关闭的连接
        String username = (String) session.getAttributes().get("username");
        String identity = (String) session.getAttributes().get("identity");
        sessions.remove(username);
        System.out.println(identity + " : " + username + " 已断开连接");
        onlineCount--;

        broadcastOnlineCount(username, false);
    }


    // 发送当前在线人数
    private void broadcastOnlineCount(String username, Boolean online) {
        for (WebSocketSession session : sessions.values()) {
            // 从会话属性中获取身份信息，假设身份属性名为"identity"，根据实际情况调整
            String identity = (String) session.getAttributes().get("identity");
            try {
                if ("emp".equals(identity)) {
                    BroadcastOnline broadcastOnline = new BroadcastOnline();
                    broadcastOnline.setUsername(username);
                    broadcastOnline.setOnline(online);
                    broadcastOnline.setOnlineCount(onlineCount);
                    broadcastOnline.setType("broadcastOnline");
                    session.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(broadcastOnline)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertMessage(Message msg){
        // 0：文本消息 1：照片 2：视频 3：语音
        if (msg.getMessageType() != 0){
            if (msg.getMessage() != null){
                String link = "/server-resource" + msg.getMessage().split("/server-resource")[1]; //http://192.168.100.134:8080/server-resource/converse/image/d45578f9-a072-41ff-9db3-36fd94f522b5.jpg
                msg.setMessage(link);
            }
            if (!StringUtil.isEmpty(msg.getMessageCover())  && !msg.getMessageCover().equals("null")){
                String link = "/server-resource" + msg.getMessageCover().split("/server-resource")[1]; //http://192.168.100.134:8080/server-resource/converse/image/d45578f9-a072-41ff-9db3-36fd94f522b5.jpg
                msg.setMessageCover(link);
            }
        }
        converseService.insertOneMessage(msg);

    }

    private void assignEmp(String customer) throws IOException {
        EmpWorkLoadVO minEmp = null;
        AssignCustomerServer assignCustomerServer = new AssignCustomerServer();
        assignCustomerServer.setType("assignCustomerServer");
        assignCustomerServer.setResult(true);

        WebSocketSession receiverSession = sessions.get(customer);

        List<String> empOnlineList = new ArrayList<>();
        for (WebSocketSession session : sessions.values()) {
            // 从会话属性中获取身份信息，假设身份属性名为"identity"，根据实际情况调整
            String identity = (String) session.getAttributes().get("identity");
            if ("emp".equals(identity)) {
                String username = (String) session.getAttributes().get("username");
                empOnlineList.add(username);
            }
        }

        System.out.println("员工在线列表-------------》" + empOnlineList);
        if (empOnlineList.size() == 1){
            //只有一个客服在线，直接选定
            String empUsername = empOnlineList.get(0);

            assignCustomerServer.setUsername(empUsername);
            receiverSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(assignCustomerServer)));

            System.out.println("分配员工：" + empUsername);
            return;
        }else if (empOnlineList.isEmpty()){
            // 如果没有在线的员工客服，给客户发送提示

            assignCustomerServer.setResult(false);
            assignCustomerServer.setReason("当前暂无在线员工客服，请稍后再试");
            receiverSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(assignCustomerServer)));

            System.out.println("当前暂无在线员工客服，无法分配");
            return;
        }


        // 获取今天的日期（LocalDate类型）
        LocalDate today = LocalDate.now();

        // 获取今天的开始时间（LocalDateTime类型，00:00:00）
        LocalDateTime startDateTime = LocalDateTime.of(today, LocalTime.MIN);

        // 获取今天的结束时间（LocalDateTime类型，23:59:59）
        LocalDateTime endDateTime = LocalDateTime.of(today, LocalTime.MAX);

        List<EmpWorkLoadVO> empWorkLoadVOS = converseService.countEmpWorkLoad(empOnlineList, String.valueOf(startDateTime), String.valueOf(endDateTime));

        for (EmpWorkLoadVO emp : empWorkLoadVOS) {
            if (null == minEmp){
                minEmp = emp;
            } else {
                if (minEmp.getWorkLoad() > emp.getWorkLoad()) {
                    minEmp = emp;
                }
            }
        }

        assert minEmp != null;
        assignCustomerServer.setUsername(minEmp.getUsername());
        receiverSession.sendMessage(new TextMessage(new ObjectMapper().writeValueAsString(assignCustomerServer)));

        System.out.println("分配员工：" + minEmp.getUsername());
    }

    // 系统发送消息
    private void systemctlSendMessage(){

    }

    // 获取在线人数
    public static int getOnlineCount() {
        return onlineCount;
    }

    // 获取连接用户
    public static List<String> getConnectedUsers() {
        return new ArrayList<>(sessions.keySet());
    }
}

