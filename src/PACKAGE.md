# 패키지 구조
```bash
│── src
│   ├── main
│   │   ├── java/kr/pe/heylocal/crawling
│   │   │   ├── config : Spring 설정 패키지
│   │   │   ├── controller : 컨트롤러 패키지
│   │   │   ├── crawler : 크롤링 로직 패키지
│   │   │   ├── dto : DTO 패키지
│   │   │   ├── mapper : Entity <-> DTO 변환 패키지
│   │   │   └── service : 서비스 비즈니스 로직 패키지
│   │   └── resources : 설정 파일, 개발용 SQL 스크립트 관리
└   └── test : 테스트 코드
```