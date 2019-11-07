package com.gantch.nbiot.transform;

import com.gantch.nbiot.httpRequest.httpRequest;
import com.gantch.nbiot.service.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by rongshuai on 2019/9/11
 */
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private UplinkService uplinkService;
    private NbiotDeviceService nbiotDeviceService;
    private NbiotTokenRelationService nbiotTokenRelationService;
    private DeviceMessageDao deviceMessageDao;
    private httpRequest hr = new httpRequest();
    private DataService dataService = new DataService();
    String ips = null;
    public UdpServerHandler(UplinkService uplinkService,NbiotDeviceService nbiotDeviceService,NbiotTokenRelationService nbiotTokenRelationService,DeviceMessageDao deviceMessageDao){
        this.uplinkService = uplinkService;
        this.nbiotDeviceService = nbiotDeviceService;
        this.nbiotTokenRelationService = nbiotTokenRelationService;
        this.deviceMessageDao = deviceMessageDao;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        System.out.println("UdpServer channelRead0");
        String HEXES = "0123456789ABCDEF";
        byte[] req = new byte[msg.content().readableBytes()];//Hex.decodeHex(data);//将接收到的字符串转换为16进制的字节数组
        msg.content().readBytes(req);
        final StringBuilder hex = new StringBuilder(2 * req.length);

        for (int i = 0; i < req.length; i++) {
            byte b = req[i];
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        System.out.println("数据长度为：" + req.length);
        System.out.println("hex:" + hex);
        System.out.println("UDP Server Receive message => " + hex);//输出接收到的数据
        Channel channel = ctx.channel();
        byte[] byteMac = {req[1],req[2],req[3],req[4],req[5],req[6],req[7],req[8]};
        char[] charMac = Hex.encodeHex(byteMac);//data.substring(2,18);
        String mac = new String(charMac);
        byte frameType = req[9];//获取数据帧的类型
        int dataLength = req[10];//有效数据长度
        byte[] validData = new byte[dataLength];//有效数据数组
        for(int i=0;i<dataLength-1;i++){
            validData[i] = req[11 + i];
        }
        if(frameType == (byte) 0x81){//当前的帧类型为“硬件请求数据”
            System.out.println("成功接收到设备数据");
            System.out.println("有效数据长度为：" + dataLength);
            System.out.println("mac:" + mac);
            UdpServer.getMap().put(mac,channel);//绑定设备的mac地址以及channel
            dataService.resolution(validData,dataLength,mac,nbiotTokenRelationService,nbiotDeviceService,deviceMessageDao);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
    private static String toHex(byte[] buf) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<buf.length;i++) {
            int high = ((buf[i]>>4) & 0x0f);// 取高4位
            int low = buf[i] & 0x0f;  //取低4位
            sb.append(high>9?((char)(high-10)+'a'):(char)(high+'0'));
            sb.append(low>9?((char)(low-10)+'a'):(char)(low+'0'));
        }
        return sb.toString().toUpperCase();
    }
}
