# study-logs
공부한 내용을 코드로 기록해요 

학습 주제별로 Branch 생성

### springboot-v1 
1. autoconfiguration  
  - conditional 종류

2. monitoring<br>
   (1) Health Checker 구현 - 서버 2대 (api-server, vendor-server)
      - AbstractFailureAnalyzer : App boot 시점 health check
      - Actuator > HealthIndicator : 운영 시점 health check
