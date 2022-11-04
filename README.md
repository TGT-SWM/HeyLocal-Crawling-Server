# 현지야 크롤링 서버
## 프로젝트 설명
현지야 서비스에서 제공하는 여러 데이터을 크롤링하기 위한 서버.  
카카오 장소 검색 API에서 제공하지 않는 정보를 직접 크롤링하여 제공한다.

<br/>

## 디렉토리 구조
```bash
├── .github
│   └── workflows : Github Action 관련 스크립트
└── gradle
│   └── wrapper : 빌드에 필요한 Gradle 래퍼 파일
└── src
│   ├── main : 메인 소스코드
└   └── test : 테스트 소스코드
``` 
패키지 구조는 아래 md 파일 참고  
[PACKAGE.md](https://github.com/TGT-SWM/HeyLocal-Crawler/blob/main/src/PACKAGE.md)

<br/>

## 환경
### 기술 스택
<span><img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"></span>
<span><img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"></span>
<span><img src="https://img.shields.io/badge/mariadb-003545?style=for-the-badge&logo=mariadb&logoColor=white"></span>
<span><img src="https://img.shields.io/badge/selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white"></span>
<span><img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"></span>

### 개발 환경
- 개발 운영체제 : macOS Monterey 12.6
- OpenJDK 18
- WebDriver : ChromeDriver 107.0.5304.62 (mac - arm64)
- WebBrowser : Chrome 107.0.5304.62 (mac - arm64)

### 배포 환경
- 서버 운영체제 : Amazon Linux 2
- AWS Services
    - EC2
    - RDS
- DB (Cache)
    - MariaDB 10.6.8
- WebDriver : ChromeDriver 107.0.5304.62 (linux - 64)
- WebBrowser : Chrome 107.0.5304.62 (linux - 64)

### 사용 라이브러리
- `spring-boot-starter-web 2.6.10`
- `spring-boot-starter-validation 2.6.10`
- `spring-boot-starter-data-jpa 2.6.10`
- `mapstruct 1.5.2`
- `selenium-java 4.5.3`

<br/>

## 프로젝트 결과물
### API
| Method | EndPoint                                        | 설명                                                |
|--------|-------------------------------------------------|---------------------------------------------------|
| ------ | 장소                                              | ------                                            |
| GET    | `/place/menu`                                   | 장소의 정보 조회                                         |



## 팀
### 기여
|이름| 구현                                                                   |
|---|----------------------------------------------------------------------|
|우태균| - 크롤링 로직 구현 <br/> - API 구현 <br/> - Infra 설계 및 구현 <br/> - CI/CD 환경 구현 |

### 팀원
|   |이름|역할|GitHub|Blog|
|---|---|---|-----|-----|
|<img src="https://avatars.githubusercontent.com/u/76861101?v=4" alt="drawing" width="100"/>|우태균|TL/Server|[GitHub 프로필](https://github.com/TaegyunWoo)|[개발 블로그](https://taegyunwoo.github.io/)|
|<img src="https://avatars.githubusercontent.com/u/76616101?v=4" alt="drawing" width="100"/>|신우진|Server/Client|[GitHub 프로필](https://github.com/gintooooonic)|[개발 블로그](https://woodyshin.com/)|
|<img src="https://avatars.githubusercontent.com/u/37467592?v=4" alt="drawing" width="100"/>|최정인|Client|[GitHub 프로필](https://github.com/choijungp)|[개발 블로그](https://velog.io/@choijungp)|

<br/><br/>

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

<br/>

최종 수정일 : 2022-11-04