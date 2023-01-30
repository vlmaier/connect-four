class OperatingSystem(var name: String = "macOS")

class DualBoot {
    var primaryOs = OperatingSystem("Windows 11")
    var secondaryOs = OperatingSystem("Arch Linux")
}