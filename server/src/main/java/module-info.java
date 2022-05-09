module ru.gb.server {
    requires org.slf4j;
    requires java.sql;

    requires io.netty.transport;
    requires io.netty.codec;
    requires io.netty.common;
    requires common;
    exports ru.gb.server;
}