# Playframework + kamon


In order to run the example we need set the -javaagent option to the JVM, but Play have some limitations when trying to set an
java agent in Play dev mode (ie, play run) -> https://github.com/playframework/playframework/issues/1372, so we have others options:

## You can set

  `-javaagent: path-to-aspectj-weaver` in your IDE or

## Run the following commands from console:

 * 1- play stage
 * 2- cd target/universal/stage
 * 3- java -cp ".:lib/*" -javaagent:lib/org.aspectj.aspectjweaver-1.8.1.jar play.core.server.NettyServer

and finally for test:

```
 curl -i -H 'X-Trace-Token:kamon-test' -H 'MyTraceLocalStorageKey:extra-header' -X GET "http://localhost:9000/helloKamon"
```

we should get:

```
 HTTP/1.1 200 OK
 Content-Type: text/plain; charset=utf-8
 MyTraceLocalStorageKey: extra-header -> Extra Information
 X-Trace-Token: kamon-test -> default Trace-Token

 Say hello to Kamon
```
