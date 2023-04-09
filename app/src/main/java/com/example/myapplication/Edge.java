package com.example.myapplication;


public class Edge {
    private int id;			// 역 번호
    private int time;		// 이동 시간
    private int distance;	// 이동 거리
    private int cost;		// 이동 비용
    private int transfer;	// 환승 획수
    Edge next = null;

    public void setId(int id) {
        this.id = id;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    public int getId() {
        return id;
    }
    public int getTime() {
        return time;
    }
    public int getDistance() {
        return distance;
    }
    public int getCost() {
        return cost;
    }
    public int getTransfer() {
        return transfer;
    }

    public String toString() {
        return id +"," + time + ", "+ distance +" ";
    }
}
