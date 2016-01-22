.class public Lcom/shell/NativeApplication;
.super Ljava/lang/Object;
.source "NativeApplication.java"


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 9
    const-string v0, "/data/data/com.example.encoderTest/.lib/libexec.so"

    invoke-static {v0}, Ljava/lang/System;->load(Ljava/lang/String;)V

    .line 10
    const-string v0, "/data/data/com.example.encoderTest/.lib/libexecmain.so"

    invoke-static {v0}, Ljava/lang/System;->load(Ljava/lang/String;)V

    .line 7
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 7
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static native load(Landroid/app/Application;Ljava/lang/String;)Z
.end method

.method public static native run(Landroid/app/Application;Ljava/lang/String;)Z
.end method

.method public static native runAll(Landroid/app/Application;Ljava/lang/String;)Z
.end method
