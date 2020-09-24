FROM openjdk:11-jre-slim

# create user and user group to avoid permission issue
RUN groupadd -g 999 appuser \
    && useradd -r -u 999 -g appuser appuser \
    && mkdir -p /app/ \
    && chown -R appuser:appuser /app
WORKDIR /app
USER appuser

RUN mkdir -p /app/data && chown -R appuser:appuser /app/data

# image port and volume settings
EXPOSE 8080
VOLUME /app/data

# copy executable jar
COPY --chown=appuser:appuser ./target/todo-*.jar todo.jar

ENTRYPOINT exec java $JAVA_OPTS -jar todo.jar