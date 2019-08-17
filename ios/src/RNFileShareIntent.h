#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
@interface RNFileShareIntent : NSObject<RCTBridgeModule>
+(void) setShareFileIntentModule_itemProvider: (NSItemProvider*) itemProvider;
+(void) setContext: (NSExtensionContext*) context;
@end
