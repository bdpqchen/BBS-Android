package com.twtstudio.bbs.bdpqchen.bbs.search.model

/**
 * Created by bdpqchen on 17-10-19.
 */

class Model {

    /**
     * err : 0
     * data : [{"id":23485,"name":"S.K.P"},{"id":27257,"name":"s253281489"},{"id":7340,"name":"saannaj"},{"id":6760,"name":"sabar"},{"id":20671,"name":"saber"},{"id":24328,"name":"Sabergunner"},{"id":6463,"name":"saberlove"},{"id":6712,"name":"sabrina"},{"id":7149,"name":"sabrinachou"},{"id":7453,"name":"SabrinaYang"},{"id":6578,"name":"sachem"},{"id":6888,"name":"sacred"},{"id":6903,"name":"sadblue"},{"id":6123,"name":"saddam"},{"id":6551,"name":"sadman"}]
     */

    var err: Int = 0
    var data: List<DataBean>? = null

    class DataBean {
        /**
         * id : 23485
         * name : S.K.P
         */

        var id: Int = 0
        var name: String? = null
    }
}
