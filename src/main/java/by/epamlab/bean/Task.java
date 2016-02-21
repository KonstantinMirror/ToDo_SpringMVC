package by.epamlab.bean;

import by.epamlab.resources.Constants;

import java.sql.Date;


public class Task {

    private long idTask;
    private String description;
    private Constants.Status status;
    private Date creationDate;
    private Date executionDate;
    private String fileName;

    public Task() {
    }

    public Task(long idTask, String description, Constants.Status status,
                Date creationDate, Date executionDate, String fileName) {
        this.idTask = idTask;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.executionDate = executionDate;
        this.fileName = fileName;
    }



    public long getIdTask() {
        return idTask;
    }

    public void setIdTask(long idTask) {
        this.idTask = idTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Constants.Status getStatus() {
        return status;
    }

    public void setStatus(Constants.Status status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = Constants.Status.valueOf(status);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
