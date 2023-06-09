package bg3.mod.character.progression.generator.layout.form

import com.vaadin.flow.component.{AttachEvent, ComponentUtil, DetachEvent}
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.icon.{Icon, VaadinIcon}
import com.vaadin.flow.component.internal.JavaScriptBootstrapUI
import com.vaadin.flow.component.menubar.MenuBar
import com.vaadin.flow.shared.Registration

class GeneratorMenuBar() extends MenuBar() {
  val generate = new Button("Generate progression", new Icon(VaadinIcon.AUTOMATION));
  generate.setEnabled(false)
  addItem(generate)
  generate.addClickListener(event => {
    ComponentUtil.fireEvent(getUI.get(), new events.GenerateProgression(this))
  })
  private var registration: Registration | Null = null

  protected override def onAttach(attachEvent: AttachEvent): Unit = {
    super.onAttach(attachEvent)
    // Register to events from the event bus
    registration = ComponentUtil.addListener(attachEvent.getUI, classOf[events.FormValidation], event => {
      generate.setEnabled(event.valid)
    })
  }

  protected override def onDetach(detachEvent: DetachEvent): Unit = {
    super.onDetach(detachEvent)
    // Unregister from the event bus
    registration.remove
  }

}
