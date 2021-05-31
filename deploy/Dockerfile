FROM mcr.microsoft.com/java/jdk:11-zulu-ubuntu

COPY ./app/backend-1.0 /app

WORKDIR /app/bin

EXPOSE 9000

RUN chmod +x /app/bin/backend

ENTRYPOINT ["/app/bin/backend"]
CMD ["-Dplay.http.secret.key=someRandomKey"]

# ENTRYPOINT ["sleep"]
# CMD sleep 3600