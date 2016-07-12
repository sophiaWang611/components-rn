/**
 * Created by sophia on 16/7/12.
 *
 * @providesModule MSComponents
 */
'use strict';

const MSComponents = {
    get MSActionSheet() { return require('MSActionSheet'); },
    get MSDatePicker() { return require('MSDatePicker'); },
    get MSHtmlView() { return require('MSHtmlView'); },
    get MSModal() { return require('MSModal'); },
    get MSPicker() { return require('MSPicker'); },
    get MSRefreshableList() { return require('MSRefreshableListView'); },
    get MSRefreshableScroll() { return require('MSRefreshableScrollView'); },
    get MSSlider() { return require('MSSlider'); },
    get MSTableView() { return require('MSTableView'); },
    get MSTextInput() { return require('MSTextInput'); },
    get MSCircleText() { return require('MSCircleTextView'); },
    get MSEvaluate() { return require('MSEvaluate'); },
    get MSImageEditor() { return require('MSImageEditor'); },
    get MSLoading() { return require('MSLoadingView'); },
    get MSMapView() { return require('MSMapView'); },
    get MSPhotoPicker() { return require('MSPhotoPicker'); },
    get MSRadio() { return require('MSRadioView'); },
    get MSScrollableTabView() { return require('MSScrollableTabView'); },
    get MSSearchBar() { return require('MSSearchBar'); },
    get MSSwiper() { return require('MSSwiperView'); },
    get MSSwitch() { return require('MSSwitchView'); },
    get MSTextArea() { return require('MSTextAreaView'); },
    get MSWebView() { return require('MSWebView'); }
};

module.exports = MSComponents;