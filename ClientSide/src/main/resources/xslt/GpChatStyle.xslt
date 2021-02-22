<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


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

                    .left-header{
                    text-align: left;
                    width: 100%;
                    margin-bottom: 0.5rem;
                    }

                    .left-content {
                    float: left;
                    max-width: 60%;
                    padding: 5px;
                    border:  #f1f1f1 1px solid;
                    border-radius: 5px;
                    background-color: #6ec1d0;
                    word-wrap: break-word;
                    }


                    .right-header{
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
                    background-color: #ddd;
                    word-wrap: break-word;

                    }

                    /* Chat containers */
                    .container {
                    padding: 10px;
                    margin: 10px 0;
                    }

                    /* Darker chat container */
                    .darker {
                    border-color: #ccc;

                    }

                    /* Clear floats */
                    .container::after {
                    content: "";
                    clear: both;
                    display: table;
                    }



                    /* Style images */
                    .container img {
                    float: left;
                    max-width: 60px;
                    width: 100%;
                    margin-right: 20px;
                    border-radius: 50%;
                    }

                    /* Style the right image */
                    .container img.right {
                    float: right;
                    margin-left: 20px;
                    margin-right:0;
                    }

                    /* Style time text */
                    .time-right {
                    float: right;
                    color: #aaa;
                    }

                    /* Style time text */
                    .time-left {
                    float: left;
                    color: #999;
                    }

                </style>
            </head>
            <body>
                <h2>Messages</h2>

                    <xsl:for-each select="gpmessages/message">
                        <xsl:sort select="timeStamp" />

                        <xsl:choose>
                        <xsl:when test="fromCurrentUser='true'">
                            <div class="container">
                                <img src="https://demo.bootstrap.news/bootnews/assets/img/avatar/avatar2.png" alt="Avatar"/>
                                <div class="left-header">
                                    <strong>
                                        <xsl:value-of select="senderName" />
                                    </strong>
                                </div>
                                <div class="left-content">
                                    <p ><xsl:value-of select="msgContent"/></p>
                                    <span class="time-right"><xsl:value-of select="timeStamp"/></span>
                                </div>
                            </div>
                        </xsl:when>

                            <xsl:otherwise>
                                <div class="container">
                                    <img src="https://demo.bootstrap.news/bootnews/assets/img/avatar/avatar1.png" alt="Avatar" class="right"/>
                                    <div class="right-header">
                                        <strong>
                                            <xsl:value-of select="senderName" />
                                        </strong>
                                    </div>
                                    <div class="right-content">
                                        <p ><xsl:value-of select="msgContent"/></p>
                                        <span class="time-left"><xsl:value-of select="timeStamp"/></span>
                                    </div>
                                </div>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:for-each>

            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>