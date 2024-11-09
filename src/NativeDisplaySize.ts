import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
  getDimensions(): {
    realWindowHeight: number;
    realWindowWidth: number;
    statusBarHeight: number;
    softMenuBarHeight: number;
    smartBarHeight: number;
    softMenuBarEnabled: boolean;
  };
}

export default TurboModuleRegistry.getEnforcing<Spec>('DisplaySize');
