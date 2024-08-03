package com.example.labbayk.tracking;

import java.util.List;

public class DirectionsResponse {
    public List<Route> routes;

    public static class Route {
        public OverviewPolyline overview_polyline;
    }

    public static class OverviewPolyline {
        public String points;
    }
}
