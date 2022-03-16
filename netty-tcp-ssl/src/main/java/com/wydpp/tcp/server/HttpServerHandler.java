package com.wydpp.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Set;

import static io.netty.handler.codec.http.HttpHeaderNames.*;

/**
 * @author wydpp
 */
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 客户端连接会触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Channel active......");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
        System.out.println(this);
        System.out.println(httpRequest.getClass().getName());
        if (httpRequest.method().equals(HttpMethod.POST)) {
            System.err.println("===this is http post===");
        }
        DecoderResult decoderResult = httpRequest.decoderResult();
        if (decoderResult.isSuccess()){
            ByteBuf buf = httpRequest.content();
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String requestContent = new String(req, "utf-8");
            System.out.println("请求内容:" + requestContent);
            writeResponse(httpRequest, requestContent, ctx.channel());
        }
        log.error("",decoderResult.cause());

    }

    private void writeResponse(FullHttpRequest request, String requestContent, Channel channel) {
        // Convert the response content to a ChannelBuffer.
        ByteBuf buf = Unpooled.copiedBuffer(requestContent, CharsetUtil.UTF_8);
        // Decide whether to close the connection or not.
        boolean close = request.headers().contains(CONNECTION, HttpHeaders.Values.CLOSE, true)
                || request.protocolVersion().equals(HttpVersion.HTTP_1_0)
                && !request.headers().contains(CONNECTION, HttpHeaders.Values.KEEP_ALIVE, true);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(CONTENT_TYPE, request.headers().get(CONTENT_TYPE));
        if (!close) {
            // There's no need to add 'Content-Length' header
            // if this is the last response.
            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        }
        Set<Cookie> cookies;
        String value = request.headers().get(COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
            cookies = CookieDecoder.decode(value);
        }
        if (!cookies.isEmpty()) {
            // Reset the cookies if necessary.
            for (Cookie cookie : cookies) {
                response.headers().add(SET_COOKIE, ServerCookieEncoder.encode(cookie));
            }
        }
        // Write the response.
        ChannelFuture future = channel.writeAndFlush(response);
        // Close the connection after the write operation is done if necessary.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }


    /**
     * 发生异常触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
