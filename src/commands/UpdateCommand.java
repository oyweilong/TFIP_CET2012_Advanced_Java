package commands;

import data.Receiver;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String payload;
    private String originalpayload;

    public UpdateCommand(Receiver receiver, int index, String payload)
    {
        this.receiver = receiver;
        this.index = index;
        this.payload = payload;
        this.originalpayload = receiver.tempDatastore.get(index).getFirst();
    }

//    private String[] parsePayload(String payload){
//        String[] payloadArr = payload.split(" ");
//
//        for (int i = 0; i < payloadArr.length; i++) {
//            payloadArr[i] = payloadArr[i].trim();
//        }
//
//        return payloadArr;
//    }
    @Override
    public boolean execute(){
        return receiver.updateEntry(index, this.parsePayload(payload));
    }
}
