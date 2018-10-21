package com.example.mario.whiteboard;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

// Class to manage Client chat Box.
public class ChatClient {

    ChatAccess access;
    PaintView pv;

    public ChatClient(PaintView pv) {
        this.pv = pv;
    }

    public void send(String s) {
        access.send(s);
    }

    public void close() {
        access.close();
    }

    /** Chat client access */
    class ChatAccess {
        private Socket socket;
        private OutputStream outputStream;

        /**
         * Create socket, and receiving thread
         */
        public void InitSocket(String server, int port) throws IOException {

            socket = new Socket(server, port);
            outputStream = socket.getOutputStream();

            Thread receivingThread = new Thread() {
                @Override
                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.charAt(0) == '<') {
                                String name_board = pv.getName();
                                String name_read = "";
                                int i = 1;
                                while(line.charAt(i) != '>') {
                                    name_read += line.charAt(i);
                                    ++i;
                                }
                                ++i;

                                if (!name_read.equals("caca")) {
                                    String line_S = line.substring(i);
                                    if (line_S.charAt(1) == 'C') { pv.clearCanvas(); }
                                    else pv.drawLine(line_S);
                                }

                            }
                            else if (line.charAt(0) == '*') { //broadcast to tots

                            }
                        }
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            };
            receivingThread.start();
        }

        private static final String CRLF = "\r\n"; // newline

        /**
         * Send a line of text
         */
        public void send(final String text) {
            AsyncTask.execute(new Runnable() {
            @Override
                public void run() {
                try {
                    outputStream.write((text + CRLF).getBytes());
                    outputStream.flush();
                } catch (IOException ex) {
                    System.out.println(ex);
                    }
                }
            });
        }


        /**
         * Close the socket
         */
        public void close() {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }
    }

    public void startConnection(final String name) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String server = "martiferret41.diskstation.me";
              //  String server = "192.168.43.221";
                int port =2222;
                access = new ChatAccess();

                try {
                    access.InitSocket(server,port);
                    access.send(name);
                } catch (IOException ex) {
                    System.out.println("Cannot connect to " + server + ":" + port);
                    ex.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }
}