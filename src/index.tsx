// @ts-ignore
import * as DisplaySize from './DisplaySize';
import { Platform } from 'react-native';

const isAndroid = Platform.OS === 'android';

export function getRealWindowHeight(): number {
  return isAndroid ? DisplaySize.getRealWindowHeight() : 0;
}

export function getRealWindowWidth(): number {
  return isAndroid ? DisplaySize.getRealWindowWidth() : 0;
}

export function getSoftMenuBarHeight(): number {
  return isAndroid ? DisplaySize.getSoftMenuBarHeight() : 0;
}

export function getStatusBarHeight(): number {
  return isAndroid ? DisplaySize.getStatusBarHeight() : 0;
}

export function getSmartBarHeight(): number {
  return isAndroid ? DisplaySize.getSmartBarHeight() : 0;
}

export function getSoftMenuBarEnabled(): boolean {
  return isAndroid ? DisplaySize.getSoftMenuBarEnabled() : false;
}
