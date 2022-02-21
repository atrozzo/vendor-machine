package com.vendor.machine

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VmachineApplication

fun main(args: Array<String>) {
    runApplication<VmachineApplication>(*args)
}
