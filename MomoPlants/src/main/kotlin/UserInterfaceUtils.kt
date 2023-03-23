class UserInterfaceUtils {
    companion object {
        public fun cleanScreen() {
            repeat(100) {
                print("\n")
            }
        }

        public fun sleep() {
            Thread.sleep(1000)
        }
    }
}