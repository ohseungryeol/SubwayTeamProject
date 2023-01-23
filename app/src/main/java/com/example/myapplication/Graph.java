package com.example.myapplication;

public class Graph {
    private String station[];	// 역 번호에 따른 역 이름
    private Edge gragh[];		// 그래프
    private int route[];		// 음수면 방문x, 음수가 아니면 방문
    int n;						// 지하철 역 개수
}
