package com.example.appremote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.*;
import java.net.*;
import java.util.Enumeration;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String ip;
        deviceSearch instance = new deviceSearch();
        new Thread(instance).start();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (instance.getIp() == null) {
            System.out.println("Using default IP");
            ip = "192.168.0.140";
        }else {
            ip = instance.getIp();
        }



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton volUp = (FloatingActionButton) findViewById(R.id.volUp);
        volUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "A";
                send_message(sentence, ip);

        }});

        FloatingActionButton volDown = (FloatingActionButton) findViewById(R.id.volDown);
        volDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "B";
                send_message(sentence, ip);
        }});

        FloatingActionButton brightUp = (FloatingActionButton) findViewById(R.id.brightUp);
        brightUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "C";
                send_message(sentence, ip);
        }});

        FloatingActionButton brightDown = (FloatingActionButton) findViewById(R.id.brightDown);
        brightDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "D";
                send_message(sentence, ip);
            }});

        FloatingActionButton pause = (FloatingActionButton) findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "E";
                send_message(sentence, ip);
            }});

        FloatingActionButton previous = (FloatingActionButton) findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "F";
                send_message(sentence, ip);
            }});

        FloatingActionButton next = (FloatingActionButton) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "G";
                send_message(sentence, ip);
            }});

        FloatingActionButton full = (FloatingActionButton) findViewById(R.id.full);
        full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String sentence = "H";
                send_message(sentence, ip);
            }});
    }
    private void send_message(final String message, final String ip){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Socket clientSocket = new Socket(ip, 65432);
                    DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
                    outToServer.writeBytes(message);
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public class deviceSearch implements Runnable{
        String ip;
        public void run(){
            System.out.println("searching LAPTOP-18CSI10M");
            String subnet = "192.168.0.";
            String host;
            String response;
            String myip = getSelfIp();


            try {
                System.out.println("My IP: "+myip);
                for(int i = 100; i<=254 ; i++){
                    host= subnet +i;
                    if (host.equals(myip)) {
                        System.out.println("IP is phone");
                        continue;
                    }
                    System.out.println("pinging " + host + ":" + 65432);
                    InetAddress inet = InetAddress.getByName(host);
                    if(inet.isReachable(50)) {
                        ip=host;
                        break;

                    }
                }


            } catch (UnknownHostException e) {
                ip="192.168.0.140";
                System.out.println("UnknownHostException");
                System.out.println("using default ip");
                System.out.println(e);
            } catch (IOException e){
                ip="192.168.0.140";
                System.out.println("IOException");
                System.out.println("using default ip");
                System.out.println(e);
            }
        }

        public String getIp(){
            return ip;
        }
    }

    private String getSelfIp() {
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces
                        .nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while(nias.hasMoreElements()) {
                    InetAddress ia= (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

