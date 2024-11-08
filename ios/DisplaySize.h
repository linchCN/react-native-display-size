
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNDisplaySizeSpec.h"

@interface DisplaySize : NSObject <NativeDisplaySizeSpec>
#else
#import <React/RCTBridgeModule.h>

@interface DisplaySize : NSObject <RCTBridgeModule>
#endif

@end
