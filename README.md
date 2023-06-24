<h1 align="middle"> ❜ </h1>

<br/>

 <p align="middle">업무라는 무한루프 속에서 <br> 지속적인 맺음을 만들어 <br> 지쳐가는 생활에 쉼표를.</p>

<div align="center">
    <p dir="auto">
        <a href="https://donggi-lee-bit.github.io/comma/src/main/resources/static/docs/index.html">
            <img src="https://img.shields.io/badge/API Docs-6DB33F?style=flat&logo=spring&logoColor=white">
        </a>
        <a href="https://github.com/donggi-lee-bit/comma/wiki">
            <img src="https://img.shields.io/badge/GitHub Wiki 📚-181717?style=flat&logo=Github&logoColor=white">
        </a>
    </p>
</div>

- [서비스 소개 📝](#서비스-소개-)
- [실행 방법](#실행-방법-)
  - [Local 환경에서의 실행](#Local-환경에서의-실행-) 

# 서비스 소개 📝

회고는 새롭게 시작할 수 있는 힘을 주고, 지났던 것들을 평가할 수 있게 합니다.

지난 일들을 확인하고, 지난 일에 대한 감정이나 상황을 스스로 또는 팀원들과 공유하여

앞으로 더 나은 우리가 되기 위해 어떤 것을 해야하는지 찾을 수 있도록 도와줍니다.

---

# 실행 방법

환경

- Java 11
- Spring Boot 2.7.6

## Local 환경에서의 실행

Local 환경에서는 MySQL 을 사용하고 있습니다. <br>
Local 환경을 실행하려면 
1. Docker 를 실행하고 
2. 빌드 후 실행합니다.

```bash
# 경로 : project root

# 실행 전 Local 환경에 도커가 켜져있어야 합니다.

# 도커 컴포즈를 이용하여 mysql, prometheus, grafana 실행

# docker-compose run
docker-compose -f platform/db/docker-compose.yml up -d

# gradlew 의 실행 권한이 없다면 권한 정보를 수정합니다.
chmod +x gradlew

# build & test
./gradlew clean build

# spring execute
> java -jar ./build/libs/comma-donggi.jar --spring.profiles-active=local
```

<details>
<summary> +a) Docker MySQL 연결 확인 </summary>
<div markdown="1">

```shell
> docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS         PORTS                               NAMES
57b4f0e18d7e   mysql:8.0.29   "docker-entrypoint.s…"   28 minutes ago   Up 2 minutes   0.0.0.0:3306->3306/tcp, 33060/tcp   local-mysql

> docker exec -it local-mysql /bin/bash
> mysql -u donggi -p
Enter password: dlehdrl12#

mysql> use comma
Database changed

mysql> show tables;
+-----------------+
| Tables_in_comma |
+-----------------+
| comma           |
| comment         |
| likes           |
| users           |
+-----------------+
4 rows in set (0.00 sec)

mysql> select * from comma limit 3;
+----+----------------------------+----------------------------+----------------+---------+-------+----------+------+---------+
| id | created_at                 | updated_at                 | content        | deleted | title | username | view | user_id |
+----+----------------------------+----------------------------+----------------+---------+-------+----------+------+---------+
|  1 | 2007-12-03 10:30:12.000000 | 2007-12-03 10:30:12.000000 | 1번째 회고 게시글입니다. |       0 | 회고    | donggi   |    0 |       1 |
|  2 | 2007-12-03 10:31:12.000000 | 2007-12-03 10:31:12.000000 | 2번째 회고 게시글입니다. |       0 | 회고    | donggi   |    0 |       1 |
|  3 | 2007-12-03 10:32:12.000000 | 2007-12-03 10:32:12.000000 | 3번째 회고 게시글입니다. |       0 | 회고    | donggi   |    0 |       1 |
+----+----------------------------+----------------------------+----------------+---------+-------+----------+------+---------+
3 rows in set (0.00 sec)
```

</div>
</details>

<br/>

<details>
<summary> +a) Prometheus, Granafa 연결 확인 </summary>
<div markdown="1">

**Mac OS 를 기준으로 된 설정입니다. Windows 에서 실행할 경우 `docker.for.mac.localhost` 대신 `host.docker.internal` 을 사용합니다.**

1. 브라우저 실행
2. `http://localhost:9090` 로 프로메테우스를 실행합니다. status -> targets 에서 연결을 확인합니다. <br>
   ![img_1.png](images/img_1.png)
3. `http://localhost:3000` 로 그라파나를 실행합니다. 처음 실행시 id/pw 를 **admin/admin** 으로 접속합니다. 비밀번호를 한 번 바꾼 뒤 `Welcome To Grafana` 페이지를 보게됩니다. <br>
   ![img_2.png](images/img_2.png)
4. 그라파나에서 data source(프로메테우스) 를 연결합니다. 왼쪽 아래의 톱니바퀴를 눌러 `Data sources` 에 접근합니다. Settings 의 HTTP URL에 `http://docker.for.mac.localhost:9090` 을 입력하여 프로메테우스를 연결합니다.
5. 그라파나에서 프로메테우스가 수집한 메트릭을 보기 위해 `Dashboard` 를 띄워주어야합니다. Dashboards -> import 에서 `import via grafana.com` 에 `6756` 을 입력하여 대시보드를 만들어줍니다. (6756은 spring boot statistics 를 보여주는 대시보드 템플릿 id입니다.) <br>
   ![img_3.png](images/img_3.png)

</div>
</details>


[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdonggi-lee-bit%2Fcomma&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
