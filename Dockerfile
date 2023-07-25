
# 자바 버전
FROM openjdk:11

# 도커 파일에서 사용할 변수
ARG JAR_FILE=build/libs/app.jar

# 해당 프로잭트에 있는 jar파일 복사
COPY ${JAR_FILE} ./app.jar

# 환경 변수 : 여기서는 time zone만 설정
ENV TZ=Asia/Seoul

# 실행할 명령을 기록 (list 형태로 입력)
ENTRYPOINT ["java","-jar","./app.jar"]

# 이후 도커 build 로 이미지 화
                 # repo 이름 / (해당 어플리케이션이름) (도커파일이 위치한 경로)
# docker build -t shh0429/application-project-test .
