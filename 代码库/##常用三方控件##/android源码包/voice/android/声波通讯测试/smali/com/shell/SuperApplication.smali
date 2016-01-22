.class public Lcom/shell/SuperApplication;
.super Landroid/app/Application;
.source "SuperApplication.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 19
    invoke-direct {p0}, Landroid/app/Application;-><init>()V

    return-void
.end method

.method private copyLib(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/io/File;)V
    .locals 6
    .param p1, "zf"    # Ljava/util/zip/ZipFile;
    .param p2, "ze"    # Ljava/util/zip/ZipEntry;
    .param p3, "file"    # Ljava/io/File;

    .prologue
    .line 103
    :try_start_0
    invoke-virtual {p3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v5

    invoke-virtual {v5}, Ljava/io/File;->exists()Z

    move-result v5

    if-nez v5, :cond_0

    .line 104
    invoke-virtual {p3}, Ljava/io/File;->getParentFile()Ljava/io/File;

    move-result-object v5

    invoke-virtual {v5}, Ljava/io/File;->mkdirs()Z

    .line 105
    :cond_0
    invoke-virtual {p1, p2}, Ljava/util/zip/ZipFile;->getInputStream(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;

    move-result-object v2

    .line 106
    .local v2, "in":Ljava/io/InputStream;
    new-instance v4, Ljava/io/FileOutputStream;

    invoke-direct {v4, p3}, Ljava/io/FileOutputStream;-><init>(Ljava/io/File;)V

    .line 107
    .local v4, "out":Ljava/io/FileOutputStream;
    const/16 v5, 0x400

    new-array v0, v5, [B

    .line 109
    .local v0, "buffer":[B
    :goto_0
    invoke-virtual {v2, v0}, Ljava/io/InputStream;->read([B)I

    move-result v3

    .local v3, "len":I
    const/4 v5, -0x1

    if-ne v3, v5, :cond_1

    .line 112
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V

    .line 113
    invoke-virtual {v4}, Ljava/io/FileOutputStream;->close()V

    .line 114
    const/4 v2, 0x0

    .line 115
    const/4 v4, 0x0

    .line 120
    .end local v0    # "buffer":[B
    .end local v2    # "in":Ljava/io/InputStream;
    .end local v3    # "len":I
    .end local v4    # "out":Ljava/io/FileOutputStream;
    :goto_1
    return-void

    .line 110
    .restart local v0    # "buffer":[B
    .restart local v2    # "in":Ljava/io/InputStream;
    .restart local v3    # "len":I
    .restart local v4    # "out":Ljava/io/FileOutputStream;
    :cond_1
    const/4 v5, 0x0

    invoke-virtual {v4, v0, v5, v3}, Ljava/io/FileOutputStream;->write([BII)V
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 116
    .end local v0    # "buffer":[B
    .end local v2    # "in":Ljava/io/InputStream;
    .end local v3    # "len":I
    .end local v4    # "out":Ljava/io/FileOutputStream;
    :catch_0
    move-exception v1

    .line 117
    .local v1, "e":Ljava/io/IOException;
    invoke-virtual {v1}, Ljava/io/IOException;->printStackTrace()V

    goto :goto_1
.end method

.method public static getCRC32(Ljava/io/File;)J
    .locals 10
    .param p0, "file"    # Ljava/io/File;

    .prologue
    .line 130
    invoke-virtual {p0}, Ljava/io/File;->exists()Z

    move-result v8

    if-nez v8, :cond_1

    .line 131
    const-wide/16 v3, 0x0

    .line 162
    :cond_0
    :goto_0
    return-wide v3

    .line 133
    :cond_1
    new-instance v5, Ljava/util/zip/CRC32;

    invoke-direct {v5}, Ljava/util/zip/CRC32;-><init>()V

    .line 134
    .local v5, "crc32":Ljava/util/zip/CRC32;
    const/4 v6, 0x0

    .line 135
    .local v6, "fileinputstream":Ljava/io/FileInputStream;
    const/4 v1, 0x0

    .line 136
    .local v1, "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    const-wide/16 v3, 0x0

    .line 138
    .local v3, "crc":J
    :try_start_0
    new-instance v7, Ljava/io/FileInputStream;

    invoke-direct {v7, p0}, Ljava/io/FileInputStream;-><init>(Ljava/io/File;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 139
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .local v7, "fileinputstream":Ljava/io/FileInputStream;
    :try_start_1
    new-instance v2, Ljava/util/zip/CheckedInputStream;

    invoke-direct {v2, v7, v5}, Ljava/util/zip/CheckedInputStream;-><init>(Ljava/io/InputStream;Ljava/util/zip/Checksum;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_7
    .catchall {:try_start_1 .. :try_end_1} :catchall_1

    .line 140
    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .local v2, "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    const/16 v8, 0x400

    :try_start_2
    new-array v0, v8, [B

    .line 141
    .local v0, "buffer":[B
    :cond_2
    invoke-virtual {v2, v0}, Ljava/util/zip/CheckedInputStream;->read([B)I

    move-result v8

    const/4 v9, -0x1

    if-ne v8, v9, :cond_2

    .line 143
    invoke-virtual {v5}, Ljava/util/zip/CRC32;->getValue()J
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_8
    .catchall {:try_start_2 .. :try_end_2} :catchall_2

    move-result-wide v3

    .line 147
    if-eqz v7, :cond_3

    .line 149
    :try_start_3
    invoke-virtual {v7}, Ljava/io/FileInputStream;->close()V
    :try_end_3
    .catch Ljava/io/IOException; {:try_start_3 .. :try_end_3} :catch_6

    .line 154
    :cond_3
    :goto_1
    if-eqz v2, :cond_7

    .line 156
    :try_start_4
    invoke-virtual {v2}, Ljava/util/zip/CheckedInputStream;->close()V
    :try_end_4
    .catch Ljava/io/IOException; {:try_start_4 .. :try_end_4} :catch_2

    move-object v1, v2

    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_0

    .line 144
    .end local v0    # "buffer":[B
    :catch_0
    move-exception v8

    .line 147
    :goto_2
    if-eqz v6, :cond_4

    .line 149
    :try_start_5
    invoke-virtual {v6}, Ljava/io/FileInputStream;->close()V
    :try_end_5
    .catch Ljava/io/IOException; {:try_start_5 .. :try_end_5} :catch_3

    .line 154
    :cond_4
    :goto_3
    if-eqz v1, :cond_0

    .line 156
    :try_start_6
    invoke-virtual {v1}, Ljava/util/zip/CheckedInputStream;->close()V
    :try_end_6
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_1

    goto :goto_0

    .line 157
    :catch_1
    move-exception v8

    goto :goto_0

    .line 146
    :catchall_0
    move-exception v8

    .line 147
    :goto_4
    if-eqz v6, :cond_5

    .line 149
    :try_start_7
    invoke-virtual {v6}, Ljava/io/FileInputStream;->close()V
    :try_end_7
    .catch Ljava/io/IOException; {:try_start_7 .. :try_end_7} :catch_4

    .line 154
    :cond_5
    :goto_5
    if-eqz v1, :cond_6

    .line 156
    :try_start_8
    invoke-virtual {v1}, Ljava/util/zip/CheckedInputStream;->close()V
    :try_end_8
    .catch Ljava/io/IOException; {:try_start_8 .. :try_end_8} :catch_5

    .line 161
    :cond_6
    :goto_6
    throw v8

    .line 157
    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v0    # "buffer":[B
    .restart local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :catch_2
    move-exception v8

    move-object v1, v2

    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_0

    .line 150
    .end local v0    # "buffer":[B
    :catch_3
    move-exception v8

    goto :goto_3

    :catch_4
    move-exception v9

    goto :goto_5

    .line 157
    :catch_5
    move-exception v9

    goto :goto_6

    .line 150
    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v0    # "buffer":[B
    .restart local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :catch_6
    move-exception v8

    goto :goto_1

    .line 146
    .end local v0    # "buffer":[B
    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    :catchall_1
    move-exception v8

    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_4

    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :catchall_2
    move-exception v8

    move-object v1, v2

    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_4

    .line 144
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :catch_7
    move-exception v8

    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_2

    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :catch_8
    move-exception v8

    move-object v1, v2

    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_2

    .end local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .end local v6    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v0    # "buffer":[B
    .restart local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v7    # "fileinputstream":Ljava/io/FileInputStream;
    :cond_7
    move-object v1, v2

    .end local v2    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    .restart local v1    # "checkedinputstream":Ljava/util/zip/CheckedInputStream;
    move-object v6, v7

    .end local v7    # "fileinputstream":Ljava/io/FileInputStream;
    .restart local v6    # "fileinputstream":Ljava/io/FileInputStream;
    goto :goto_0
.end method


# virtual methods
.method protected attachBaseContext(Landroid/content/Context;)V
    .locals 1
    .param p1, "base"    # Landroid/content/Context;

    .prologue
    .line 22
    invoke-super {p0, p1}, Landroid/app/Application;->attachBaseContext(Landroid/content/Context;)V

    .line 24
    invoke-virtual {p0, p1}, Lcom/shell/SuperApplication;->loadLibs(Landroid/content/Context;)V

    .line 27
    const-string v0, "com.example.encoderTest"

    invoke-static {p0, v0}, Lcom/shell/NativeApplication;->load(Landroid/app/Application;Ljava/lang/String;)Z

    .line 28
    return-void
.end method

.method protected copyLib(Ljava/lang/String;)V
    .locals 10
    .param p1, "cpuabi"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Throws;
        value = {
            Ljava/io/IOException;
        }
    .end annotation

    .prologue
    .line 57
    const-string v0, "assets/ijm_lib/armeabi/libexec.so"

    .line 58
    .local v0, "libExec":Ljava/lang/String;
    const-string v1, "assets/ijm_lib/armeabi/libexecmain.so"

    .line 59
    .local v1, "libExecMain":Ljava/lang/String;
    const-string v6, "x86"

    invoke-virtual {p1, v6}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v6

    if-eqz v6, :cond_0

    .line 60
    const-string v0, "assets/ijm_lib/x86/libexec.so"

    .line 61
    const-string v1, "assets/ijm_lib/x86/libexecmain.so"

    .line 63
    :cond_0
    new-instance v3, Ljava/io/File;

    .line 64
    const-string v6, "/data/data/com.example.encoderTest/.lib/libexec.so"

    .line 63
    invoke-direct {v3, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 65
    .local v3, "libExecOutFile":Ljava/io/File;
    new-instance v2, Ljava/io/File;

    .line 66
    const-string v6, "/data/data/com.example.encoderTest/.lib/libexecmain.so"

    .line 65
    invoke-direct {v2, v6}, Ljava/io/File;-><init>(Ljava/lang/String;)V

    .line 68
    .local v2, "libExecMainOutFile":Ljava/io/File;
    new-instance v5, Ljava/util/zip/ZipFile;

    invoke-virtual {p0}, Lcom/shell/SuperApplication;->getPackageCodePath()Ljava/lang/String;

    move-result-object v6

    invoke-direct {v5, v6}, Ljava/util/zip/ZipFile;-><init>(Ljava/lang/String;)V

    .line 70
    .local v5, "zf":Ljava/util/zip/ZipFile;
    invoke-virtual {v5, v0}, Ljava/util/zip/ZipFile;->getEntry(Ljava/lang/String;)Ljava/util/zip/ZipEntry;

    move-result-object v4

    .line 75
    .local v4, "ze":Ljava/util/zip/ZipEntry;
    if-eqz v4, :cond_1

    invoke-virtual {v4}, Ljava/util/zip/ZipEntry;->getCrc()J

    move-result-wide v6

    invoke-static {v3}, Lcom/shell/SuperApplication;->getCRC32(Ljava/io/File;)J

    move-result-wide v8

    cmp-long v6, v6, v8

    if-eqz v6, :cond_1

    .line 77
    invoke-direct {p0, v5, v4, v3}, Lcom/shell/SuperApplication;->copyLib(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/io/File;)V

    .line 80
    :cond_1
    invoke-virtual {v5, v1}, Ljava/util/zip/ZipFile;->getEntry(Ljava/lang/String;)Ljava/util/zip/ZipEntry;

    move-result-object v4

    .line 85
    if-eqz v4, :cond_2

    invoke-virtual {v4}, Ljava/util/zip/ZipEntry;->getCrc()J

    move-result-wide v6

    invoke-static {v2}, Lcom/shell/SuperApplication;->getCRC32(Ljava/io/File;)J

    move-result-wide v8

    cmp-long v6, v6, v8

    if-eqz v6, :cond_2

    .line 86
    invoke-direct {p0, v5, v4, v2}, Lcom/shell/SuperApplication;->copyLib(Ljava/util/zip/ZipFile;Ljava/util/zip/ZipEntry;Ljava/io/File;)V

    .line 89
    :cond_2
    const/4 v4, 0x0

    .line 90
    invoke-virtual {v5}, Ljava/util/zip/ZipFile;->close()V

    .line 91
    const/4 v5, 0x0

    .line 92
    return-void
.end method

.method protected loadLibs(Landroid/content/Context;)V
    .locals 3
    .param p1, "base"    # Landroid/content/Context;

    .prologue
    .line 39
    sget-object v0, Landroid/os/Build;->CPU_ABI:Ljava/lang/String;

    .line 42
    .local v0, "cpu":Ljava/lang/String;
    if-eqz v0, :cond_1

    .line 43
    :try_start_0
    const-string v2, "x86"

    invoke-virtual {v0, v2}, Ljava/lang/String;->startsWith(Ljava/lang/String;)Z

    move-result v2

    if-eqz v2, :cond_0

    .line 44
    const-string v2, "x86"

    invoke-virtual {p0, v2}, Lcom/shell/SuperApplication;->copyLib(Ljava/lang/String;)V

    .line 54
    :goto_0
    return-void

    .line 46
    :cond_0
    const-string v2, "arm"

    invoke-virtual {p0, v2}, Lcom/shell/SuperApplication;->copyLib(Ljava/lang/String;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    goto :goto_0

    .line 51
    :catch_0
    move-exception v1

    .line 52
    .local v1, "e":Ljava/lang/Exception;
    invoke-virtual {v1}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    .line 49
    .end local v1    # "e":Ljava/lang/Exception;
    :cond_1
    :try_start_1
    const-string v2, "arm"

    invoke-virtual {p0, v2}, Lcom/shell/SuperApplication;->copyLib(Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0
.end method

.method public onCreate()V
    .locals 1

    .prologue
    .line 31
    const-string v0, "android.app.Application"

    invoke-static {p0, v0}, Lcom/shell/NativeApplication;->run(Landroid/app/Application;Ljava/lang/String;)Z

    .line 32
    invoke-super {p0}, Landroid/app/Application;->onCreate()V

    .line 33
    return-void
.end method
