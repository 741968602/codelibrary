    NSAutoreleasePool *tempPool = [[NSAutoreleasePool alloc] init];
    VoicePlayer *player=[[VoicePlayer alloc] init];
    [player play:@"12345678" playCount:1 muteInterval:0];
    while (![player isStopped]) {
        usleep(300 * 1000);//300ms
    }
    [tempPool drain];