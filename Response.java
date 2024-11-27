package com.example.jobfinderclient;

import java.util.List;

import com.example.jobfinderclient.model.Job;

public class Response implements java.io.Serializable {

    private List<Job> jobs;

    public Response(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Response{" +
                "jobs=" + jobs +
                '}';
    }
}
