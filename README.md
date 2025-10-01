# Kotlin Spring Boot DynamoDB 샘플 프로젝트

이 프로젝트는 Kotlin과 Spring Boot를 사용하여 AWS DynamoDB와 연동하는 REST API 애플리케이션의 샘플입니다.

## 프로젝트 개요

-   **언어**: Kotlin 1.6.21
-   **프레임워크**: Spring Boot 2.6.7
-   **데이터베이스**: AWS DynamoDB
-   **빌드 도구**: Gradle (Kotlin DSL)
-   **Java 버전**: 11

## 주요 기능

-   상품(Product) CRUD 작업
-   DynamoDB와의 연동
-   RESTful API 제공
-   AWS 자격 증명 설정
-   개발환경용 로컬 DynamoDB 지원

## 기술 스택

### 핵심 의존성

-   **Spring Boot Starter Web**: REST API 개발
-   **Jackson Module Kotlin**: JSON 직렬화/역직렬화
-   **AWS DynamoDB SDK**: AWS DynamoDB 연동
-   **Spring Data DynamoDB**: Spring Data를 통한 DynamoDB 접근
-   **Kotlin Logging**: 로깅 라이브러리

### 개발 도구

-   **Spring Boot DevTools**: 개발 시 자동 재시작
-   **Lombok**: 보일러플레이트 코드 감소

## 프로젝트 구조

```
src/main/kotlin/com/example/sample/
├── KotlinSpringbootSampleApplication.kt  # 메인 애플리케이션
├── config/                               # 설정 클래스들
│   ├── AWSCredentialsProviderConfig.kt   # AWS 자격 증명 설정
│   ├── DynamoDbConfiguration.kt          # DynamoDB 설정
│   ├── DynamoDBSetupAfterAppStartup.kt   # 앱 시작 후 DB 설정
│   └── DynamoDBTableManager.kt           # 테이블 관리
├── controller/
│   └── ProductController.kt              # 상품 REST 컨트롤러
├── models/
│   └── ProductDocument.kt                # 상품 데이터 모델
├── repositories/
│   └── ProductRepository.kt              # 상품 데이터 접근 계층
├── service/
│   └── ProductService.kt                 # 상품 비즈니스 로직
├── extensions/
│   └── Extensions.kt                     # Kotlin 확장 함수들
└── util/
    └── InstantConverter.kt               # 날짜/시간 변환 유틸
```

## 설정

### application.yml 설정

```yaml
amazon:
    region: ap-northeast-2 # AWS 리전
    accessKey: testAccessKey # AWS Access Key
    secretKey: testSecretKey # AWS Secret Key
    dynamodb:
        endpoint: http://localhost:8000 # 로컬 DynamoDB 엔드포인트
        tablePrefix: local- # 테이블 접두사
        tables:
            vendor:
                ttlinhrs: 2 # TTL 설정 (시간)
```

## 실행 방법

### 1. 로컬 DynamoDB 설정 (선택사항)

로컬 개발을 위해 DynamoDB Local을 사용할 수 있습니다:

```bash
# DynamoDB Local 다운로드 및 실행
java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
```

### 2. 애플리케이션 실행

```bash
# Gradle을 사용한 실행
./gradlew bootRun

# 또는 JAR 파일 빌드 후 실행
./gradlew build
java -jar build/libs/kotlin-sample-0.0.1-SNAPSHOT.jar
```

## API 엔드포인트

### 상품 관련 API

| 메서드 | 엔드포인트                 | 설명           |
| ------ | -------------------------- | -------------- |
| GET    | `/product/findAll`         | 모든 상품 조회 |
| GET    | `/product/findOne?id={id}` | 특정 상품 조회 |
| POST   | `/product/save`            | 상품 저장      |
| PUT    | `/product/update`          | 상품 수정      |
| DELETE | `/product/delete?id={id}`  | 상품 삭제      |

### API 사용 예시

```bash
# 모든 상품 조회
curl -X GET http://localhost:8080/product/findAll

# 특정 상품 조회
curl -X GET "http://localhost:8080/product/findOne?id=product-123"

# 상품 저장
curl -X POST http://localhost:8080/product/save \
  -H "Content-Type: application/json" \
  -d '{"id":"product-123","name":"샘플 상품","price":10000}'
```

## 테스트

프로젝트에는 다음과 같은 테스트 유틸리티가 포함되어 있습니다:

-   `CreateTable.kt`: DynamoDB 테이블 생성 테스트
-   `DropTable.kt`: DynamoDB 테이블 삭제 테스트

```bash
# 테스트 실행
./gradlew test
```

## 개발 환경 설정

### 필수 요구사항

-   **Java 11** 이상
-   **Kotlin 1.6.21**
-   **Gradle 7.x**

### IDE 설정

IntelliJ IDEA를 사용하는 것을 권장합니다:

1. Kotlin 플러그인 설치
2. Spring Boot 플러그인 설치
3. AWS Toolkit 설치 (선택사항)

## 주요 설정 클래스 설명

### DynamoDbConfiguration

-   DynamoDB 클라이언트 Bean 설정
-   AWS 자격 증명 프로바이더 설정
-   리전 및 엔드포인트 설정

### ProductDocument

-   DynamoDB 테이블과 매핑되는 엔티티 클래스
-   `@DynamoDBTable` 어노테이션으로 테이블 매핑
-   Hash Key, Range Key 설정

### ProductService

-   비즈니스 로직 처리
-   DynamoDB CRUD 작업 수행
-   트랜잭션 관리
