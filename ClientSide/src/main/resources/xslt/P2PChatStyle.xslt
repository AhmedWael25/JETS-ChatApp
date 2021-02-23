<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">


    <xsl:template match="/">
        <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>
                <style>
                    body {
                    margin: 0 auto;
                    max-width: 800px;
                    padding: 0 20px;

                    }
                    .container{
                    background-color: rgb(252, 237, 218);
                    }

                    .left-header {
                    text-align: left;
                    width: 100%;
                    margin-bottom: 0.5rem;
                    }

                    .left-content {
                    float: left;
                    max-width: 60%;
                    padding: 5px;
                    border: #f1f1f1 1px solid;
                    border-radius: 5px;
                    background-color: #6ec1d0;
                    word-wrap: break-word;
                    }


                    .right-header {
                    text-align: right;
                    width: 100%;
                    margin-bottom: 0.5rem;
                    }

                    .right-content {
                    float: right;
                    text-align: right;
                    max-width: 60%;
                    padding: 5px;
                    border: #e6e7e9 1px solid;
                    border-radius: 5px;
                    background-color: rgb(146, 204, 165);
                    word-wrap: break-word;

                    }

                    /* Chat messages */
                    .message {
                    padding: 10px;
                    margin: 10px 0;
                    }

                    /* Darker chat message */
                    .darker {
                    border-color: rgb(235, 232, 232);

                    }

                    /* Clear floats */
                    .message::after {
                    content: "";
                    clear: both;
                    display: table;
                    }


                    /* Style images */
                    .message img {
                    float: left;
                    width:50px;
                    height:50px;
                    margin-right: 20px;
                    border-radius: 50%;

                    }

                    /* Style the right image */
                    .message img.right {
                    float: right;
                    margin-left: 20px;
                    margin-right: 0;
                    }

                    /* Style time text */
                    .time-right {
                    float: right;
                    color: rgb(255, 255, 255);
                    }

                    /* Style time text */
                    .time-left {
                    float: left;
                    color: rgb(255, 255, 255);
                    }
                </style>
            </head>
            <body>
                <h2>Peer To Peer Messages</h2>

                <div class="container">

                    <xsl:for-each select="P2PMessages/message">
                        <xsl:sort select="timeStamp"/>

                        <xsl:choose>
                            <xsl:when test="fromCurrentUser='false'">
                                <div class="message">
                                    <img alt="Avatar">
                                        <xsl:attribute name="src">
                                            <xsl:value-of select="imagePath"/>
                                        </xsl:attribute>
                                    </img>
                                    <div class="left-header">
                                        <strong>
                                            <xsl:value-of select="senderName"/>
                                        </strong>
                                    </div>
                                    <div class="left-content">
                                        <p>
                                            <xsl:value-of select="msgContent"/>
                                        </p>
                                        <span class="time-right">
                                            <xsl:value-of select="timeStamp"/>
                                        </span>
                                    </div>
                                </div>
                            </xsl:when>

                            <xsl:otherwise>
                                <div class="message">
                                    <!--attribute value template >> ="{some expression here}" -->
                                    <img src="{imagePath}" alt="Avatar" class="right"/>
                                    <div class="right-header">
                                        <strong>
                                            <xsl:value-of select="senderName"/>
                                        </strong>
                                    </div>
                                    <div class="right-content">
                                        <p>
                                            <xsl:value-of select="msgContent"/>
                                        </p>
                                        <span class="time-left">
                                            <xsl:value-of select="timeStamp"/>
                                        </span>
                                    </div>
                                </div>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>