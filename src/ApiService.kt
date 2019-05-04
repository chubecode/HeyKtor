package chutien.it


interface Api {
    suspend fun getForex(base: String = "", target: String = "")
}