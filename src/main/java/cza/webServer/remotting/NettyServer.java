
/**  
* @Title: NettyServer.java
* @Package cza.webServer.remotting
* @Description: TODO(用一句话描述该文件做什么)
* @author Administrator
* @date 2016年11月30日下午3:45:44
* @version V1.0  
*/

package cza.webServer.remotting;

import java.net.SocketAddress;

import org.apache.tools.ant.taskdefs.optional.jsp.JspC;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @ClassName: NettyServer
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Administrator
 * @date 2016年11月30日下午3:45:44
 *
 */

public class NettyServer {
	public NettyServer() {

	}

	public void startServer() throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						ChannelPipeline cl = ch.pipeline();
						 cl.addLast(new HttpResponseEncoder());
						cl.addLast( new HttpRequestDecoder());
						cl.addLast(new HttpObjectAggregator(10000));
						cl.addLast(new ServerHandler());

					}
				});
		ChannelFuture f = bootstrap.bind(8080).sync();
	}
}

class ServerHandler extends SimpleChannelInboundHandler<Object> {
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param ctx
	    * @param msg
	    * @throws Exception
	    * @see io.netty.channel.SimpleChannelInboundHandler#messageReceived(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	    */
	    
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			DefaultFullHttpRequest request = (DefaultFullHttpRequest) msg;
			ByteBuf requestContent=request.content();
			byte[] bs=new byte[requestContent.readableBytes()];
			requestContent.readBytes(bs);
			System.out.println(new String(bs));
			String resp = "<html><head><title>cs</title></head><body>aaa</body></html>";
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(resp.getBytes("UTF-8")));
			response.headers().set("Content-Type", "text/html");
			response.headers().set("Content-Length", response.content().readableBytes());
			ctx.write(response);
			ctx.flush();
			JspC jspc=new JspC();
			jspc.execute();
		} else if (msg instanceof HttpContent) {
			HttpContent content=(HttpContent)msg;
			byte[] bts=new byte[content.content().readableBytes()];
			content.content().readBytes(bts);
			System.out.println(new String(bts));
			System.out.println(111);
			String resp = "<html><head><title>cs</title></head><body>aaa</body></html>";
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,Unpooled.wrappedBuffer(resp.getBytes("UTF-8")));
			response.headers().set("Content-Type", "text/html");
			response.headers().set("Content-Length", response.content().readableBytes());
			ctx.write(response);
			ctx.flush();
		}
		
	}

}
