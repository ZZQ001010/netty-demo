package com.zzq.netty3x.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    static Scanner scanner = new Scanner(System.in);



    public static Channel init (){
        //创建启动对象
        ClientBootstrap bootstrap = new ClientBootstrap();

        //创建nio工厂
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        bootstrap.setFactory(new NioClientSocketChannelFactory(boss,worker));

        //设置管道工厂
        bootstrap.setPipelineFactory(()->{
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("hiHandler", new HiHandler());
            return pipeline;
        });

        ChannelFuture connect = bootstrap.connect(new InetSocketAddress(8888));
        return connect.getChannel();

    }

    public static String systemIn(){
        while (scanner.hasNext()){
           return  scanner.next();
        }
        return  null;
    }





    public static void main(String[] args) {
        Channel channel = init();
        String s = systemIn();
        channel.write(s);
    }



    static  class  HiHandler  extends SimpleChannelHandler{
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            System.out.println(e.getMessage());
            super.messageReceived(ctx, e);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            super.exceptionCaught(ctx, e);
        }

        @Override
        public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            super.channelOpen(ctx, e);
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            super.channelConnected(ctx, e);
        }

        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            super.channelDisconnected(ctx, e);
        }

        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            super.channelClosed(ctx, e);
        }
    }
}
