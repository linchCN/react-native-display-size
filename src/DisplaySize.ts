import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-display-size' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const DisplaySizeModule = isTurboModuleEnabled
  ? require('./NativeDisplaySize').default
  : NativeModules.DisplaySize;

const DisplaySize = DisplaySizeModule
  ? DisplaySizeModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function getRealWindowHeight(): number {
  return DisplaySize.getDimensions().realWindowHeight;
}

export function getRealWindowWidth(): number {
  return DisplaySize.getDimensions().realWindowWidth;
}

export function isStatusBarTranslucent(): boolean {
  return DisplaySize.isStatusBarTranslucent();
}

export function getSoftMenuBarHeight(): number {
  return DisplaySize.getDimensions().softMenuBarHeight;
}

export function getStatusBarHeight(): number {
  return DisplaySize.getDimensions().statusBarHeight;
}

export function getSmartBarHeight(): number {
  return DisplaySize.getDimensions().smartBarHeight;
}

export function getSoftMenuBarEnabled(): boolean {
  return DisplaySize.getDimensions().softMenuBarEnabled;
}
