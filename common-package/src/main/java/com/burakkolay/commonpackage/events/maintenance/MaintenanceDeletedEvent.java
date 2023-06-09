package com.burakkolay.commonpackage.events.maintenance;

import com.burakkolay.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDeletedEvent implements Event {
    private UUID carId;
}
