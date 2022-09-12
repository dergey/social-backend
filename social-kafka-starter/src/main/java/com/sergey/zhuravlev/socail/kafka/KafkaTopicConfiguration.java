package com.sergey.zhuravlev.socail.kafka;

public class KafkaTopicConfiguration {

    private String name;
    private String concurrency;
    private String groupId;
    private Boolean synchro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(String concurrency) {
        this.concurrency = concurrency;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Boolean getSynchro() {
        return synchro;
    }

    public void setSynchro(Boolean synchro) {
        this.synchro = synchro;
    }
}
