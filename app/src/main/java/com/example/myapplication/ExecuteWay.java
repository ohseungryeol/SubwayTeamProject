package com.example.myapplication;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class ExecuteWay {
    // 총 역의 개수는 146(station.txx 번호 매긴것들)
    public static final int StationSum = 146;
    int n = StationSum;

    // 제일 작은 값을 선택, 지나온 역 개수, 환승역인지 체크
    private int min,countStn, checkStn;

    // 스택
    Stack<Integer> stackRoute = new Stack<>();
    Stack<Integer> reverseStackRoute = new Stack<>();
    Stack<Integer> stnStack = new Stack<>();
    Stack<Integer> reverseStnStack = new Stack<>();


    //station.txt 파일을 불러올 reader
    InputStreamReader streamReader;
    // 역의 정보 임시 저장할 Edge
    Edge tempEdge;
    // 역과 역을 연결
    Edge vertexConnecter;
    //맞는 역만 입력받게끔 하기 위한 boolean 배열 선언
    public boolean Check[] = new boolean[1000];
    // 메인 그래프
    private Edge graph[] = new Edge[n];
    // 음수면 방문x, 음수가 아니면 방문
    int route[] = new int[n];

    // 역 번호에 따른 역 이름배열
    int station[] = new int[n];

    // 경로 찾기시 임시 스트링변수
    String tempStn = "";
    String tempStnNode = "";
    String[] splitStnNode;
    // 인덱스 0은 출발역, 1은 중간역들, 2는 도착역
    private String outputStn = "          이용 경로\n";

    // 매개변수로 넘겨받은 출발역과 도착역
    int inputStartStn, inputEndStn;

    // 출발역과 도착역의 정점번호를 의미, 실행될 모드
    int start = -1, end = -1, mode;

    // 지나온 역 개수를 반환
    public String getCountStn(){
        return Integer.toString(countStn - 1);
    }
    // 역 순서를 반환
    public String getOutputStn(){
        return outputStn;
    }
    // 각각 지나온 시간, 환승횟수, 비용, 거리를 반환
    public String getRouteTime(){
        return graph[end].getTime()/3600 + "시간 " + (graph[end].getTime() % 3600) / 60 +"분 " + graph[end].getTime() % 60 + "초";
    }
    public String getRouteTranster(){
        return Integer.toString(graph[end].getTransfer());
    }
    public String getRouteCost(){
        return Integer.toString(graph[end].getCost());
    }
    public String getRouteDistance(){
        return Integer.toString(graph[end].getDistance());
    }

    public String getInformation() {
        String info = "";

        info = "\n  "+ getCountStn() + "개 역을 지납니다.";
        info += "\n  시간 : " + getRouteTime();
        info += "\n  환승 : " + getRouteTranster() +" 회";
        info += "\n  비용 : " + getRouteCost() +" 원";
        info += "\n  거리 : " + getRouteDistance() + " m";

        return info;
    }

    // 출발역, 도착역, 모드, 텍스트파일 읽은 것을 받아온다.
    public ExecuteWay(String inputStartStn, String inputEndStn, int mode, InputStreamReader streamReader){
        this.inputStartStn = Integer.parseInt(inputStartStn);
        this.inputEndStn = Integer.parseInt(inputEndStn);
        this.mode = mode;
        this.streamReader = streamReader;
    }

    public void runExecuteWay() throws IOException {

        // 모든 그래프에 공간 할당
        for (int i = 0; i < n; i++) {
            graph[i] = new Edge();
        }

        // station.txt값 저장하기
        String str = "";
        BufferedReader buf = new BufferedReader(streamReader);

        // 임시값을 저장할 변수 선언
        int tempStart, tempEnd, tempTime, tempDis, tempCost;
        int startStn, endStn;

        // 끝이 나올때까지 실행
        while ((str = buf.readLine()) != null){
            // 공백을 기준으로 한줄을 잘라서 배열에 저장
            String[] tempArr = str.split(" ");

            // 출발역 노드번호
            tempStart = Integer.parseInt(tempArr[0]);
            // 도착역 노드번호
            tempEnd = Integer.parseInt(tempArr[1]);
            // 출발역 역이름
            startStn = Integer.parseInt(tempArr[2]);
            // 도착역 역이름
            endStn = Integer.parseInt(tempArr[3]);
            // 역 사이의 시간
            tempTime = Integer.parseInt(tempArr[4]);
            // 역 사이의 거리
            tempDis = Integer.parseInt(tempArr[5]);
            // 역 사이의 비용
            tempCost = Integer.parseInt(tempArr[6]);

            // 유효한 값의 역정보는 true로 전환
            Check[startStn] = true;
            Check[endStn] = true;

            // 노드가 0부터 시작이므로 1씩 감소해서 저장
            tempStart -= 1;
            tempEnd -= 1;

            // 임시 간선을 생성 후, 간선에 도착역 정보 저장
            tempEdge = new Edge();
            tempEdge.setId(tempEnd);
            tempEdge.setTime(tempTime);
            tempEdge.setDistance(tempDis);
            tempEdge.setCost(tempCost);

            // 입력받은 역이 환승역일 경우 환승회수 +1
            if(startStn == endStn){
                tempEdge.setTransfer(1);
            } else {
                tempEdge.setTransfer(0);
            }

            // 간선으로 연결하는 과정
            vertexConnecter = graph[tempStart];
            while(vertexConnecter.next != null){
                vertexConnecter = vertexConnecter.next;
            }
            vertexConnecter.next = tempEdge;

            // 임시 간선을 생성 후, 간선에 출발역 정보 저장
            tempEdge = new Edge();
            tempEdge.setId(tempStart);
            tempEdge.setTime(tempTime);
            tempEdge.setDistance(tempDis);
            tempEdge.setCost(tempCost);

            // 입력받은 역이 환승역일 경우
            if(startStn == endStn){
                tempEdge.setTransfer(1);
            } else {
                tempEdge.setTransfer(0);
            }

            // 간선으로 연결
            vertexConnecter = graph[tempEnd];
            while(vertexConnecter.next != null){
                vertexConnecter = vertexConnecter.next;
            }
            vertexConnecter.next = tempEdge;

            // 역이름 배열에 정점번호에 맞게 역의 이름을 저장
            station[tempStart] = startStn;
            station[tempEnd] = endStn;
        }

        // 출발역과 역 이름이 같은 정점을 반환
        for(int i = 0; i < n; i++){
            if (station[i] == inputStartStn){
                start = i;
                break;
            }
        }
        // 도착역과 역 이름이 같은 정점을 반환
        for(int i = 0; i < n; i++){
            if (station[i] == inputEndStn){
                end = i;
                break;
            }
        }

        // 다익스트라 알고리즘 실행
        // 모든 정점까지의 가중치 최소를 찾기때문에 출발역과 모드만 넘김.
        Dijkstra(start,mode);

        // mode에 따라 구하는 값이 달라짐
        switch (mode) {
            case 1:
                // 최소시간 경로를 선택한다
                min = graph[end].getTime();
                for (int i = 0; i < n; i++) {
                    if (station[end] == station[i]) {
                        if (graph[i].getTime() < min) {
                            min = graph[i].getTime();
                            end = i;
                        }
                    }
                }
                break;

            case 2:
                // 최단거리 경로를 선택한다
                min = graph[end].getDistance();
                for (int i = 0; i < n; i++) {
                    if (station[end] == station[i]) {
                        if (graph[i].getDistance() < min) {
                            min = graph[i].getDistance();
                            end = i;
                        }
                    }
                }
                break;

            case 3:
                // 최소비용 경로를 선택한다
                min = graph[end].getCost();
                for (int i = 0; i < n; i++) {
                    if (station[end] == station[i]) {
                        if (graph[i].getCost() < min) {
                            min = graph[i].getCost();
                            end = i;
                        }
                    }
                }
                break;

            case 4:
                // 최소환승 경로를 선택한다
                min = graph[end].getTransfer();
                for (int i = 0; i < n; i++) {
                    if (station[end] == station[i]) {
                        if (graph[i].getTransfer() < min) {
                            min = graph[i].getTransfer();
                            end = i;
                        }
                    }
                }
                break;
        }


        FindRoute(start, end);

        String[] splitStn = tempStn.split(" ");
        splitStnNode = tempStnNode.split(" ");
        for (int i = 0 ; i <splitStn.length;i++) {

            stackRoute.push(Integer.parseInt(splitStn[i]));
        }

        while(!stackRoute.empty()){ // reverseStackRoute 에 거꾸로 쌓는 과정.
            reverseStackRoute.push(stackRoute.peek());
            stackRoute.pop();

            reverseStnStack.push(stnStack.peek());
            stnStack.pop();
        }

        for (countStn = 0, checkStn = -1; !reverseStackRoute.empty();countStn++){
            if(checkStn != -1 && checkStn == reverseStackRoute.peek()){
                if(reverseStackRoute.peek() != station[start] && reverseStackRoute.peek() != station[end]){
                    outputStn += "(환승)";
                }
                countStn--;
                checkStn = reverseStackRoute.peek();
                reverseStackRoute.pop();
            } else {
                if(countStn != 0){
                    outputStn += " => ";
                }
                outputStn += Integer.toString(reverseStackRoute.peek());
                checkStn = reverseStackRoute.peek();
                reverseStackRoute.pop();
            }
        }
        outputStn += "\n\n              \n";
    }



    public void Dijkstra(int start, int mode) {

        int minpos;

        // 간선으로 연결된 역들의 graph를 초기화 한다.
        for (int i = 0; i < n; i++) {

            route[i] = -1;
            graph[i].setId(-1);
            graph[i].setTime(9999);
            graph[i].setDistance(9999);
            graph[i].setTransfer(9999);
            graph[i].setCost(9999);
        }

        // 출발역과 연결된 정점에, 다음 정점의 가중치들을 대입한다.
        for (Edge x = graph[start].next; x != null; x = x.next) {

            // 출발역이 환승역인 경우 모든 값을 0으로 초기화 한다.
            if (station[start] == station[x.getId()]) {
                graph[x.getId()].setTime(0);
                graph[x.getId()].setTransfer(0);
                graph[x.getId()].setDistance(0);
                graph[x.getId()].setCost(0);
                // 다음 정점의 가중치들로 초기화 한다.
            } else {
                graph[x.getId()].setTime(x.getTime());
                graph[x.getId()].setDistance(x.getDistance());
                graph[x.getId()].setTransfer(x.getTransfer());
                graph[x.getId()].setCost(x.getCost());
            }
        }

        // 환승역들을 모두 조사해서 환승역끼리의 값들을 0으로 초기화한다.
        for (int i = 0; i < n; i++) {
            if (station[start] == station[i]) {
                for (Edge x = graph[i].next; x != null; x = x.next) {
                    if (station[i] == station[x.getId()]) {
                        x.setTime(0);
                        x.setTransfer(0);
                        x.setDistance(0);
                        x.setCost(0);
                    }
                }
            }
        }
        //
        route[start] = 0;

        // 시작역 초기화
        graph[start].setTime(0);
        graph[start].setTransfer(0);
        graph[start].setDistance(0);
        graph[start].setCost(0);

        for (int i = 0; i < n - 2; i++) {
            // 현재 정점에서 연결된 정점 중 가중치가 가장 작은 정점을 찾아 선택한다.
            minpos = Choose(mode);

            route[minpos] = 0;

            // 최소시간(1), 최소비용(2), 최소거리(3), 최소환승(4)
            for (Edge x = graph[minpos].next; x != null; x = x.next) {
                switch (mode) {
                    case 1: // 최단시간을 구하는 경우
                        if (graph[minpos].getTime() + x.getTime() < graph[x.getId()].getTime()) {
                            graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                            graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                            graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                            graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            // 시간이 같은 경우 환승이 적은거로 저장한다.
                        } else if (graph[minpos].getTime() + x.getTime() == graph[x.getId()].getTime()) {
                            if (graph[minpos].getTransfer() + x.getTransfer() < graph[x.getId()].getTransfer()) {
                                graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                                graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                                graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                                graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            }
                        }
                        break;
                    case 2:// 최단거리가 같은 경우
                        if (graph[minpos].getDistance() + x.getDistance() < graph[x.getId()].getDistance()) {
                            graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                            graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                            graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                            graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            // 시간이 적게 걸리는것을 저장한다
                        } else if (graph[minpos].getDistance() + x.getDistance() == graph[x.getId()].getDistance()) {
                            if (graph[minpos].getTime() + x.getTime() < graph[x.getId()].getTime()) {
                                graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                                graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                                graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                                graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            }
                        }
                        break;
                    case 3:// 비용이 같은경우
                        if (graph[minpos].getCost() + x.getCost() < graph[x.getId()].getCost()) {
                            graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                            graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                            graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                            graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            // 시간이 적게 걸리는것을 저장한다.
                        } else if (graph[minpos].getCost() + x.getCost() == graph[x.getId()].getCost()) {
                            if (graph[minpos].getTime() + x.getTime() < graph[x.getId()].getTime()) {
                                graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                                graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                                graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                                graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            }
                        }
                        break;
                    case 4:// 환승횟수가 같은 경우
                        if (graph[minpos].getTransfer() + x.getTransfer() < graph[x.getId()].getTransfer()) {
                            graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                            graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                            graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                            graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            // 시간이 적게 걸리는것을 저장한다.
                        } else if (graph[minpos].getTransfer() + x.getTransfer() == graph[x.getId()].getTransfer()) {
                            if (graph[minpos].getTime() + x.getTime() < graph[x.getId()].getTime()) {
                                graph[x.getId()].setTime(graph[minpos].getTime() + x.getTime());
                                graph[x.getId()].setDistance(graph[minpos].getDistance() + x.getDistance());
                                graph[x.getId()].setTransfer(graph[minpos].getTransfer() + x.getTransfer());
                                graph[x.getId()].setCost(graph[minpos].getCost() + x.getCost());
                            }
                        }
                        break;
                }
            }
        }
    }

    // 지금 정점에서 인접 정점중 가중치가 가장 작은 정점을 반환한다.
    public int Choose(int selectMode) {
        // 가장 큰 수로 초기화
        int min_time = 9999, min_dis = 9999, min_transfer = 9999, min_cost = 9999, pos = -1;

        // 최소 시간, 최소 비용, 최소 거리, 최소 환승 중 선택
        switch (selectMode) {
            case 1: // 최단 시간
                for (int i = 0; i < n; i++) {
                    if (route[i] < 0) {
                        // 시간 가중치가 가장 작은 것을 선택
                        if (graph[i].getTime() < min_time) {
                            min_time = graph[i].getTime();
                            min_transfer = graph[i].getTransfer();
                            pos = i;
                            // 시간 가중치가 같은 경우
                        } else if (graph[i].getTime() == min_time) {
                            // 환승이 적은 것을 선택
                            if (graph[i].getTransfer() < min_transfer) {
                                min_time = graph[i].getTime();
                                min_transfer = graph[i].getTransfer();
                                pos = i;
                            }
                        }
                    }
                }
                break;
            case 2: // 최단 거리
                for (int i = 0; i < n; i++) {
                    if (route[i] < 0) {
                        // 거리 가중치가 가장 작은 것을 선택
                        if (graph[i].getDistance() < min_dis) {
                            min_dis = graph[i].getDistance();
                            min_transfer = graph[i].getTransfer();
                            pos = i;
                            // 거리 가중치가 같은 경우
                        } else if (graph[i].getDistance() == min_dis) {
                            // 환승이 적은 것을 선택
                            if (graph[i].getTransfer() < min_transfer) {
                                min_dis = graph[i].getDistance();
                                min_transfer = graph[i].getTransfer();
                                pos = i;
                            }
                        }
                    }
                }
                break;
            case 3: // 최소 비용
                for (int i = 0; i < n; i++) {
                    if (route[i] < 0) {
                        // 비용 가중치가 가장 작은 것을 선택
                        if (graph[i].getCost() < min_cost) {
                            min_time = graph[i].getTime();
                            min_cost = graph[i].getCost();
                            pos = i;
                            // 비용 가중치가 같은 경우
                        } else if (graph[i].getCost() == min_cost) {
                            // 시간 가중치가 작은 것을 선택
                            if (graph[i].getTime() < min_time) {
                                min_time = graph[i].getTime();
                                min_cost = graph[i].getCost();
                                pos = i;
                            }
                        }
                    }
                }
                break;
            case 4: // 최소 환승
                for (int i = 0; i < n; i++) {
                    if (route[i] < 0) {
                        // 환승 횟수가 가장 작은 것을 선택
                        if (graph[i].getTransfer() < min_transfer) {
                            min_time = graph[i].getTime();
                            min_transfer = graph[i].getTransfer();
                            pos = i;
                            // 환승 횟수가 같은 경우
                        } else if (graph[i].getTransfer() == min_transfer) {
                            // 시간 가중치가 작은 것을 선택
                            if (graph[i].getTime() < min_time) {
                                min_time = graph[i].getTime();
                                min_transfer = graph[i].getTransfer();
                                pos = i;
                            }
                        }
                    }
                }
        }
        // 연결된 정점 중 가중치가 가장 작은 정점을 반환
        return pos;
    }

    // 입력받은 역정보의 true,false 값을 반환한다.
    public boolean getCheckStation(int stn){
        return Check[stn];
    }
    // 도착역 > 출발역 트리에서 경로 찾기(역순으로 저장되어 있음)
    public boolean FindRoute(int start, int end) {

        // 역 번호 문자열 넣는다.
        stnStack.push(start);
        tempStn += station[start] + " ";
        // 시작=끝 일때 경로찾았다고 알려줌 (return true)
        if (start == end) {
            return true;
        }
        // 시작역에 연결된 간선을 모두 조사한다(트리를 하나씩 따라감)
        for (Edge p = graph[start].next; p != null; p = p.next)
        {
            // 현재 정점의 가중치 중 한 값 과 그 다음 연결된 값의 합이 연결된 정점의 값과 같을 경우
            if (graph[start].getTime() + p.getTime() == graph[p.getId()].getTime()
                    && graph[start].getTransfer() + p.getTransfer() == graph[p.getId()].getTransfer()) {
                // 재귀 호출 후 다음 경로로 이동 했는데 경로가 없다면
                if (!this.FindRoute(p.getId(), end))
                {
                    // 없으면 삭제
                    tempStn = tempStn.substring(0, tempStn.length() - 4);
                    stnStack.pop();
                    // 현재 위치에서 경로를 찾았다고 알려줌
                } else {
                    return true;
                }
            }
        }
        return false;
    }
}