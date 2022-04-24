package it.saga.egov.esicra.timer;

import it.saga.egov.esicra.attachments.Attachments;
import it.saga.egov.esicra.timer.EesTask;
import it.saga.egov.esicra.EsicraConf;

public class AttachmentsTask extends EesTask {


    public void process() {
      logger.info("Task "+this.getId()+" partito ");  
      Attachments attach = new Attachments();
      attach.execute();
      logger.info("Task "+this.getId()+" terminato ");
    }

    public static void main(String[] args) {
        EsicraConf.configura();
        Attachments test = new Attachments();
        test.execute();
    }
}
