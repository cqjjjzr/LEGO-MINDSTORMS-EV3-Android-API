package com.charlie.ev3;

import android.annotation.TargetApi;
import android.os.Build;

import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * 管理来自EV3的响应。
 * @author Charlie Jiang
 */
public class ResponseManager {
    private static short _nextSequence = 0x0001;
    private static final LinkedHashMap<Integer,Response> responses = new LinkedHashMap<>();
    private static short GetSequenceNumber()
    {
        if(_nextSequence == Integer.MAX_VALUE)
            _nextSequence = 1;

        return _nextSequence++;
    }

    /**
     * 创建一个响应。
     * @return New response
     */
    public static Response createResponse()
    {
        short sequence = GetSequenceNumber();

        Response r = new Response(sequence);
        responses.put((int)sequence,r);
        return r;
    }

    /**
     * 等待一个响应对象直到EV3响应。
     * @param r 等待的对象
     */
    public static void waitForResponse(final Response r)
    {
        new Thread(new Runnable(){
            public void run(){
                try{
                    r.event.wait(1000);
                    responses.remove((int)r.getSequence());
                }
                catch (InterruptedException ex){
                    r.setReplyType(ReplyType.DIRECT_REPLY_ERROR);
                }
            }
        });
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void handleResponse(byte[] report)
    {
        if (report == null || report.length < 3)
            return;

        short sequence = (short) (report[0] | (report[1] << 8));
        int replyType = report[2];

        //System.Diagnostics.Debug.WriteLine("Size: " + report.Length + ", Sequence: " + sequence + ", Type: " + (ReplyType)replyType + ", Report: " + BitConverter.ToString(report));

        if (sequence > 0)
        {
            Response r = responses.get((int)sequence);

            if (ReplyType.getByByte((byte)replyType)!=null)
                r.setReplyType(ReplyType.getByByte((byte)replyType));

            if (r.getReplyType() == ReplyType.DIRECT_REPLY || r.getReplyType() == ReplyType.DIRECT_REPLY_ERROR)
            {
                r.setData(Arrays.copyOfRange(report,3,report.length - 3));
            }
            else if (r.getReplyType() == ReplyType.SYSTEM_REPLY || r.getReplyType() == ReplyType.SYSTEM_REPLY_ERROR) {
                if (SystemOpCode.getByByte(report[3])!=null)
                    r.setSystemCommand(SystemOpCode.getByByte(report[3]));

                if (((int) report[4])>SystemReplyStatus.values().length)
                    r.setSystemReplyStatus(SystemReplyStatus.values()[report[4]]);
                r.setData(new byte[report.length - 5]);
                r.setData(Arrays.copyOfRange(report, 5, report.length - 5));
            }
            r.event.notifyAll();
        }
    }
}
