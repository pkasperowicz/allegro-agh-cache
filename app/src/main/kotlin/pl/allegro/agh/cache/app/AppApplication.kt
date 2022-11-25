package pl.allegro.agh.cache.app

import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AppApplication {
	@Bean
	fun timedAspect(registry: MeterRegistry): TimedAspect {
		return TimedAspect(registry)
	}
}

fun main(args: Array<String>) {
	runApplication<AppApplication>(*args)
}
