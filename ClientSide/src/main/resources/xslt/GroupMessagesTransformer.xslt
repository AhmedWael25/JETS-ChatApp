<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <html>
            <body>
                <h2>Messages</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th>Sender Id</th>
                        <th>Sender Name</th>
                        <th>Message Content</th>
                        <th>Time Stamp</th>
                        <th>Chat ID</th>
                    </tr>
                    <xsl:for-each select="gpmessages/message">
                        <xsl:sort select="timeStamp" />
                        <tr>
                            <td>
                                <xsl:value-of select="senderId" />
                            </td>
                            <td>
                                <xsl:value-of select="senderName" />
                            </td>

                            <td>
                                <xsl:value-of select="msgContent" />
                            </td>

                            <td>
                                <xsl:value-of select="timeStamp" />
                            </td>

                            <td>
                                <xsl:value-of select="chatId" />
                            </td>

                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>