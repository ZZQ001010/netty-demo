package com.zzq.netty3x.Server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static  void init (){
        //创建服务类
        ServerBootstrap bootstrap = new ServerBootstrap();

        //创建两个线程池
        ExecutorService boos =Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        //创建nio工厂
        bootstrap.setFactory(new NioServerSocketChannelFactory(boos,worker));

        //设置管道工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return null;
            }
        });

        bootstrap.setPipelineFactory(()->{
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("decoder", new StringDecoder());
            pipeline.addLast("encoder", new StringEncoder());
            pipeline.addLast("helloHandler", new HelloHandler());
            return pipeline;
        });


        bootstrap.bind(new InetSocketAddress(8888));
        System.out.println("start !");
    }




    public static void main(String[] args) {
        init();
    }













    static class  HelloHandler extends SimpleChannelHandler {
        /**
         * 接受消息
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            String msg = (String) e.getMessage();
            System.out.println(msg);
            ctx.getChannel().write("hi");
            super.messageReceived(ctx, e);
        }

        /**
         * 异常处理
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            System.out.println(e.toString());
        }

        /**
         * 通道连接的时候
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("channelConnected");

            super.channelConnected(ctx, e);
        }

        /**
         * 通道销毁连接的时候
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("channelDisconnected");
            super.channelDisconnected(ctx, e);
        }

        /**
         * 通道关闭的时候
         * @param ctx
         * @param e
         * @throws Exception
         */
        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("channelClosed");
            super.channelClosed(ctx, e);
        }
    }


}
