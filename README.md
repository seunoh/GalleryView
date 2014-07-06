# Android Gallery View
========================

안드로이드에 있는 이미지를 불러오는 소스입니다. 단순히 불러오기만 하는것이 아니라, LruCache 를 이용하여 속도를 개선 하였으며, Disk Cache 는 적용 되어 있지 않습니다.

LruCache 는 안드로이드에서 샘플로 제공하는 [DisplayingBitmap](http://developer.android.com/samples/DisplayingBitmaps/index.html). 를 참고 하였습니다.