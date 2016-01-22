//
//  VoicePlayer.h
//  VoiceEncoder
//
//  Created by godliu on 14-10-24.
//  Copyright (c) 2014年 godliu. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface VoiceEncoder : NSObject
{
    
}

- (id) init;
- (BOOL) isStopped;
- (void) play:(NSString *)_text playCount:(long)_playCount muteInterval:(int)_muteInterval;

@end
