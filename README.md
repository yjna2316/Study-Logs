### branch: study-springboot
- 서버 두대 구성 
   - api-server 8080 포트
   - vendor-server 9090 포트 - 가장 먼저 구동할것  
- vendor health check api
   - GET /vendor/health
      - 200 status 응답, 사용 가능 상태
   - GET /vendor/unhealth
      - 500 status 응답, 사용 불가 상태
- 시나리오
   - 가정: api-server는 vendor-server의 기능을 연동하여 서비스를 제공하는 서버이다. 다음 두가지 수단을 통해 헬스 체크를 구현해보자. 
   - **Case 1. Fail Analyzer**
      - 앱 시작 시점에 api-server는 vendor-server가 제공하는 health check api를 호출한다. 호출 결과 500 status가 응답되면 앱 구동을 중단하고 실패 상태를 로그로 보여준다.
      - vendor-server 서버 헬스체크 Endpoint 2개
         1. /vendor/healthy -200 Ok
         2. /vendor/unhealthy - 500 error
   - **Case 2. Actuator Indicator**
      - 5초 주기로 api-server는 vendor-server의 건강상태를 확인한다. 5번 호출 모두 200 OK 가 아니면 사용 불가상태라 판단한다.
      - 시나리오
         - 5회 이상 실패시 → 비정상
         - 5회 이내로 200OK → 정상



<img width="671" alt="image" src="https://github.com/user-attachments/assets/26cb7b66-0dff-4d07-bb90-68776a618f7b">
