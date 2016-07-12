/**
 * Created by sophia on 16/7/12.
 *
 * @providesModule MSComponents
 */
'use strict';

const MSComponents = {
    get MSActionSheet() { return require('./app/MSActionSheet'); },
    get MSDatePicker() { return require('./app/MSDatePicker'); },
    get MSHtmlView() { return require('./app/MSHtmlView'); },
    get MSModal() { return require('./app/MSModal'); },
    get MSPicker() { return require('./app/MSPicker'); },
    get MSRefreshableList() { return require('./app/MSRefreshableList'); },
    get MSRefreshableScroll() { return require('./app/MSRefreshableScroll'); },
    get MSSlider() { return require('./app/MSSlider'); },
    get MSTableView() { return require('./app/MSTableView'); },
    get MSTextInput() { return require('./app/MSTextInput'); },
    get MSCircleText() { return require('./app/MSCircleTextView'); },
    get MSEvaluate() { return require('./app/MSEvaluate'); },
    get MSImageEditor() { return require('./app/MSImageEditor'); },
    get MSLoading() { return require('./app/MSLoading'); },
    get MSMapView() { return require('./app/MSMapView'); },
    get MSPhotoPicker() { return require('./app/MSPhotoPicker'); },
    get MSRadio() { return require('./app/MSRadio'); },
    get MSScrollableTabView() { return require('./app/MSScrollableTabView'); },
    get MSSearchBar() { return require('./app/MSSearchBar'); },
    get MSSwiper() { return require('./app/MSSwiper'); },
    get MSSwitch() { return require('./app/MSSwitch'); },
    get MSTextArea() { return require('./app/MSTextArea'); },
    get MSWebView() { return require('./app/MSWebView'); },
    get MSImageCrop() {return require('./app/MSImageCrop')},
    get MSToast() {return require('./app/MSToast')},
    get MSLocationService() {return require('./app/MSLocationService')}
};

module.exports = MSComponents;