<h1 align="middle"> comma</h1>

 <p align="middle">업무라는 무한루프 속에서 <br> 지속적인 맺음을 만들어 <br> 지쳐가는 생활에 쉼표를.</p>


## 서비스 소개 📝

`회고`는 새롭게 시작할 수 있는 힘을 주고, 지났던 것들을 평가할 수 있게 합니다.

지난 일들을 확인하고, 지난 일에 대한 감정이나 상황을 `스스로` 또는 `팀원`들과 공유하여

앞으로 더 나은 우리가 되기 위해 어떤 것을 해야하는지 찾을 수 있도록 도와줍니다.

---

# 모니터링 프로그램 실행 방법
환경
- Java 8
- Spring Boot 2.7.6

## Local 환경에서 실행

현재 모니터링 프로그램을 Local 환경에서 실행하고 있습니다. <br>
<br>
모니터링 프로그램을 도커로 실행하고 있기 때문에 spring 애플리케이션 서버가 실행되는 `localhost` 와 도커 컨테이너의 `localhost` 를 통신할 수 있게 해야 합니다.
<details>
<summary> Local 환경 네트워크 설정 </summary>
<div markdown="1">

(도커를 실행할 때 `--network host` 옵션을 이용하면 로컬 환경과 동일한 네트워크에서 컨테이너를 실행한다고 하나 연결에 실패하였습니다. 추후 방법을 찾아 수정할 예정입니다.)

현재 연결된 네트워크의 ip 주소를 지정해줍니다. ip 주소는 mac 기준 시스템 환경설정 -> 네트워크에서 ip 주소를 확인합니다. 

![img.png](img.png)

해당 ip 주소를 `project root dir/monitor/prometheus-grafana/prometheus/config/prometheus.yml` 의 `scrape_configs` 에서 `targets` 부분에 작성해줍니다. (예시 : 172.30.1.60:8080)
<br><br>
프로메테우스가 spring 애플리케이션이 실행되고 있는 로컬 환경을 바라보게 됩니다.
</div>
</details>

```bash
# 경로 : project root dir/monitor/prometheus-grafana
# 실행 전 Local 환경에 도커가 켜져있어야 합니다.
# 도커 컴포즈를 이용하여 prometheus, grafana 를 한꺼번에 실행

# 도커 이미지 생성 및 컨테이너 시작
docker compose up -d
# 컨테이너 중지
docker compose stop
# 컨테이너 다시 시작
docker compose start
```

<details>
<summary> 모니터링 프로그램 연결 확인 </summary>
<div markdown="1">

1. 브라우저를 실행합니다.
2. `http://localhost:9090` 로 프로메테우스를 실행합니다. status -> targets 에서 연결을 확인합니다. <br>
   ![img_1.png](img_1.png)
3. `http://localhost:3000` 로 그라파나를 실행합니다. 처음 실행시 id/pw 를 **admin/admin** 으로 접속합니다. 비밀번호를 한 번 바꾼 뒤 `Welcome To Grafana` 페이지를 보게됩니다. <br>
   ![img_2.png](img_2.png)
4. 그라파나에서 data source(프로메테우스) 를 연결합니다. 왼쪽 아래의 톱니바퀴를 눌러 `Data sources` 에 접근합니다. Settings 의 HTTP URL에 `http://{ip address}:9090` 을 입력하여 프로메테우스를 연결합니다.
5. 그라파나에서 프로메테우스가 수집한 메트릭을 보기 위해 `Dashboard` 를 띄워주어야합니다. Dashboards -> import 에서 `import via grafan.com` 에 `6756` 을 입력하여 대시보드를 만들어줍니다. (6756은 spring boot statistics 를 보여주는 대시보드 템플릿 id입니다.) <br>
   ![img_3.png](img_3.png)

</div>
</details>


[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fdonggi-lee-bit%2Fcomma&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
