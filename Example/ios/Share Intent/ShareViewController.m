//
//  ShareViewController.m
//  Share Intent
//
//  Created by Ajith A B on 16/08/19.
//  Copyright © 2019 Facebook. All rights reserved.
//
#import "ShareViewController.h"
#import "RCTRootView.h"
#import "RCTBundleURLProvider.h"
#import "RNFileShareIntent.h"

@interface ShareViewController ()

@end

@implementation ShareViewController

- (void) loadView
{
  NSURL *jsCodeLocation;
  
  NSExtensionItem *item = self.extensionContext.inputItems.firstObject;
  NSItemProvider *itemProvider = item.attachments.firstObject;
  [RNFileShareIntent setShareFileIntentModule_itemProvider:itemProvider];
  [RNFileShareIntent setContext: self.extensionContext];
  
   jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"Share" fallbackResource:nil];
  
  RCTRootView *rootView = [[RCTRootView alloc] initWithBundleURL:jsCodeLocation
                                                      moduleName:@"Share"
                                               initialProperties:nil
                                                   launchOptions:nil];
  self.view = rootView;
}

// animate (IN)
- (void)viewWillAppear:(BOOL)animated
{
  [super viewWillAppear:animated];
  
  self.view.transform = CGAffineTransformMakeTranslation(0, self.view.frame.size.height);
  [UIView animateWithDuration:0.25 animations:^
   {
     self.view.transform = CGAffineTransformIdentity;
   }];
}


@end
